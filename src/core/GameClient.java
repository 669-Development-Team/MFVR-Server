package core;

// Java Imports
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

// Other Imports
import database.Models.User;
import metadata.Constants;
import metadata.GameRequestTable;
import networking.request.GameRequest;
import networking.response.GameResponse;
import utility.DataReader;
import utility.Log;

/**
 * The GameClient class is an extension of the Thread class that represents an
 * individual client. Not only does this class holds the connection between the
 * client and server, it is also in charge of managing the connection to
 * actively receive incoming requests and send outgoing responses. This thread
 * lasts as long as the connection is alive.
 */
public class GameClient implements Runnable {

    // Client Variables
    private String session_id;
    private Socket clientSocket;
    private InputStream inputStream; // For use with incoming requests
    private OutputStream outputStream; // For use with outgoing responses
    private DataInputStream dataInputStream; // Stores incoming requests for use
    private boolean isLoggedIn;
    private boolean isInGame;
    private boolean isDone;
    private Queue<GameResponse> updatesForClient; // List of responses to send to to the client
    private byte[] latestUpdateFromClient; // Stores the last update pushed from the client
    private int updateNumber; //How many updates have been sent to this user

    // Other Variables
    private User user;

    /**
     * Initialize the GameClient using the client socket and creating both input
     * and output streams.
     * 
     * @param session_id holds the unique identifier of this session
     * @param clientSocket holds reference of the socket being used
     * @throws IOException 
     */
    public GameClient(String session_id, Socket clientSocket) throws IOException {
        this.session_id = session_id;
        this.clientSocket = clientSocket;
        updatesForClient = new LinkedList<>();
        inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        isLoggedIn = false;
        isInGame = false;
        updateNumber = 0;
    }

    /**
     * Holds the main loop that processes incoming requests by first identifying
     * its type, then interpret the following data in each determined request
     * class. Queued up responses created from each request class will be sent
     * after the request is finished processing.
     * 
     * The loop exits whenever the isPlaying flag is set to false. One of these
     * occurrences is triggered by a timeout. A timeout occurs whenever no
     * activity is picked up from the client such as being disconnected.
     */
    @Override
    public void run() {
        long lastActivity = System.currentTimeMillis();
        short requestCode = -1;

        while (!isDone) {
            try {
                // Extract the size of the package from the data stream
                short requestLength = DataReader.readShort(dataInputStream);

                if (requestLength > 0) {
                    lastActivity = System.currentTimeMillis();
                    // Separate the remaining package from the data stream
                    byte[] buffer = new byte[requestLength];
                    inputStream.read(buffer, 0, requestLength);
                    DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(buffer));
                    // Extract the request code number
                    requestCode = DataReader.readShort(dataInput);
                    // Determine the type of request
                    GameRequest request = GameRequestTable.get(requestCode);
                    // If the request exists, process like following:
                    if (request != null) {
                        request.setGameClient(this);
                        // Pass input stream to the request object
                        request.setDataInputStream(dataInput);
                        // Parse the input stream
                        request.parse();
                        // Interpret the data
                        request.doBusiness();
                        try {
                            // Retrieve any responses created by the request object
                            for (GameResponse response : request.getResponses()) {
                                // Transform the response into bytes and pass it into the output stream
                                send(response);
                            }
                        } catch (IOException ex) {
                            Log.printf_e("Client %s connection lost", session_id);
                            isDone = true;
                        }
                    }
                } else {
                    // If there was no activity for the last moments, exit loop
                    if ((System.currentTimeMillis() - lastActivity) / 1000 >= Constants.TIMEOUT_SECONDS) {
                        isDone = true;
                    }
                }
                Thread.sleep(5);
            } catch (Exception ex) {
                Log.printf_e("Request [%d] Error:", requestCode);
                Log.println_e(ex.getMessage());
                Log.println_e("---");
                ex.printStackTrace();
            }

        }

        if (user != null) {
            removeUserData();
        }

        // Remove this GameClient from the server
        GameServer.getInstance().deletePlayerThreadOutOfActiveThreads(session_id);
        Log.printf("Client %s has ended", session_id);
    }

    /**
     * Used whenever a player exits from the game. The most recent information
     * stored for the player will be saved into the database and any ties with
     * the server will be removed as well.
     */
    public void removeUserData() {
        // Player.removePlayer(player);
        GameServer.getInstance().removeActivePlayer(user.getID());
        Log.printf("User '%s' has logged off.", user.getUserName());
    }

    public String getID() {
        return session_id;
    }

    public void end() {
        isDone = true;
    }

    public long getUserID() {
        return user != null ? user.getID() : -1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){ this.user = user;}

    public boolean addResponseForUpdate(GameResponse response) {
        return updatesForClient.add(response);
    }

    public void setLatestUpdateFromClient(byte[] response) {
        latestUpdateFromClient = response;
    }

    public byte[] getLatestUpdateFromClient() {
        return latestUpdateFromClient;
    }

    public int getUpdateNumber() {
        if(updateNumber >= Constants.maxUpdateNumber)
            updateNumber = 0;
        else
            updateNumber++;

        return  updateNumber;
    }

    public boolean loggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public boolean inGame() {
        return isInGame;
    }

    public void setInGame(boolean ingame) {
        this.isInGame = ingame;
    }

    public void send(GameResponse response) throws IOException {
        outputStream.write(response.constructResponseInBytes());
    }

    /**
     * Get all pending responses for this client.
     * 
     * @return all pending responses
     */
    public Queue<GameResponse> getUpdatesForClient() {
        Queue<GameResponse> responseList = null;

        synchronized (this) {
            responseList = updatesForClient;
            updatesForClient = new LinkedList<>();
        }

        return responseList;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public Socket getClientSocket(){return this.clientSocket; }

    /**
     * Remove all responses for this client.
     */
    public void clearUpdateBuffer() {
        updatesForClient.clear();
    }

    public String getIP() {
        return clientSocket.getInetAddress().getHostAddress();
    }
    
    public void newSession() {
        session_id = GameServer.createUniqueID();
        updatesForClient.clear();
        user = null;
    }

    @Override
    public String toString() {
        String str = "";

        str += "-----" + "\n";
        str += getClass().getName() + "\n";
        str += "\n";

        for (Field field : getClass().getDeclaredFields()) {
            try {
                str += field.getName() + " - " + field.get(this) + "\n";
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        str += "-----";

        return str;
    }
}

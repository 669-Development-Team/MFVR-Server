package core;

// Java Imports

import configuration.GameServerConf;
import database.DCM;
import database.Models.User;
import metadata.Constants;
import metadata.GameRequestTable;
import utility.ConfFileParser;
import utility.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Other Imports

/**
 * The GameServer class serves as the main module that runs the server.
 * Incoming connection requests are established and redirected to be managed
 * by another class called the GameClient. Several specialized methods are also
 * stored here to perform other specific needs.
 */
public class GameServer {

    // todo: remove this when lobbies are implemented -- it's to bypass GameLobby
    private ArrayList<String> availableCharacters;
    public String getCharacter(){
        // assign a random character to the player when adding to lobby
        int randomIndex = new Random().nextInt(availableCharacters.size());
        String character = availableCharacters.get(randomIndex);
        return character;
    }

    public void disableCharacter(String character){
        availableCharacters.remove(character);
    }


    // Singleton Instance
    private static ArrayList<User> users;
    private static GameServer gameServer;
    private static ArrayList<Integer> freePorts;

    // Server Variables
    private boolean isDone; // Server Loop Flag
    private GameServerConf configuration; // Stores server config. variables
    private ServerSocket serverSocket;
    private ExecutorService clientThreadPool;

    // Reference Tables
    private Map<String, GameClient> activeThreads = new HashMap<>(); // Session ID -> Client
    private Map<Long, User> activePlayers = new HashMap<>(); // Player ID -> Player
    private Map<Long, GameLobby> lobbies = new HashMap<>(); // Lobby ID -> GameLobby

    /**
     * Create the GameServer by setting up the request types and creating a
     * connection with the database.
     */
    public GameServer() {
        // Load configuration file
        configure();
        // Initialize tables for global use
        GameRequestTable.init(); // Contains request codes and classes
        // Initialize database connection
        if (DCM.getInstance() == null) {
            Log.println_e("Database Connection Failed!");
            System.exit(-1);
        }
        freePorts = new ArrayList<>();
        for(int i = 9000; i < 9010; i++)
            freePorts.add(i);
        users = new ArrayList<>();
        clientThreadPool = Executors.newCachedThreadPool();

        // todo: this is part of the bandaid fix
        availableCharacters = new ArrayList<>(Constants.characters.keySet());

    }

    /**
     * Load values from a configuration file.
     */
    public final void configure() {
        configuration = new GameServerConf();
        ConfFileParser confFileParser = new ConfFileParser("conf/gameServer.conf");
        configuration.setConfRecords(confFileParser.parse());
    }

    /**
     * Run the game server by waiting for incoming connection requests.
     * Establishes each connection and stores it into a GameClient to manage
     * incoming and outgoing activity.
     */
    private void run() {
        try {
            // Open a connection using the given port to accept incoming connections
            serverSocket = new ServerSocket(configuration.getPortNumber());
            Log.printf("Server has started on port: %d", serverSocket.getLocalPort());
            Log.println("Waiting for clients...");
            // Loop indefinitely to establish multiple connections
            while (!isDone) {
                try {
                    // Accept the incoming connection from client
                    Socket clientSocket = serverSocket.accept();


                    Log.printf("%s is connecting...", clientSocket.getInetAddress().getHostAddress());
                    // Create a runnable instance to represent a client that holds the client socket
                    String session_id = createUniqueID();
                    GameClient client = new GameClient(session_id, clientSocket);
                    // Keep track of the new client thread
                    addToActiveThreads(client);
                    // Initiate the client
                    clientThreadPool.submit(client);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException ex) {
            Log.println_e(ex.getMessage());
        }
    }

    public static String createUniqueID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Return the gameserver object, or create one if it doesn't exist
     *
     * @return
     */
    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    /**
     * get a port from the list of free ports, removing that port from the list
     *
     * @return
     */
    public int getFreePort(){
        int port = -1;
        if(freePorts.size() > 0) {
            port = freePorts.get(freePorts.size() - 1);
        }
        return port;
    }

    /**
     * return a port to the list of free ports
     *
     * @param port
     */
    public void freePort(int port){
        freePorts.add(port);
    }

    /**
     * return the map of active threads
     *
     * @return
     */
    public Map<String, GameClient> getActiveThreads() {
        return activeThreads;
    }

    /**
     * Get the socket the server is running on
     * @return
     */
    public ServerSocket getServerSocket(){ return this.serverSocket;}

    /**
     * Get the GameLobby bound to the Lobby ID provided, or null if the GameLobby doesn't exist
     *
     * @param ID ID of the Lobby to join
     * @return The GameLobby bound to the ID, or null, if it doesn't exist
     */
    public GameLobby getGameLobbyByID(long ID){
        return lobbies.get(ID);
    }


    /**
     * Get the GameClient thread for the player using the player ID.
     *
     * @param userID holds the user ID
     * @return the GameClient thread
     */
    public GameClient getThreadByUserID(long userID) {
        for (GameClient client : activeThreads.values()) {
            User user = client.getUser();

            if (user != null && user.getID() == userID) {
                return client;
            }
        }
        return null;
    }


    /**
     * get the list of users currently online
     * @return
     */
    public static ArrayList<User> getUsers() {
        return users;
    }

    /**
     * add a user to the list of currently online users
     * @param user
     */
    public static void addUser(User user){
        users.add(user);
    }

    /**
     * remove a user from the list of currently online users
     * @param user
     */
    public static void removeUser(User user){
        users.remove(user);
    }

    /**
     * add a thread to the map of active threads
     * @param client
     */
    public void addToActiveThreads(GameClient client) {
        activeThreads.put(client.getID(), client);
    }

    public List<User> getActivePlayers() {
        return new ArrayList<>(activePlayers.values());
    }

    public User getActivePlayer(long userID) {
        return activePlayers.get(userID);
    }

    public void setActivePlayer(User user) {
        activePlayers.put(user.getID(), user);
    }

    public void removeActivePlayer(long userID) {
        String character = getActivePlayer(userID).getCharacter();
        if(character != null) availableCharacters.add(character);
        activePlayers.remove(userID);
    }

    /**
     * Delete a player's GameClient thread out of the activeThreads
     *
     *
     * @param session_id The id of the thread.
     */
    public void deletePlayerThreadOutOfActiveThreads(String session_id) {
        activeThreads.remove(session_id);
    }

    /**
     * Initiates the Game Server by configuring and running it. Restarts
     * whenever it crashes.
     *
     * @param args contains additional launching parameters
     */
    public static void main(String[] args) {
        try {
            Log.printf("World of Balance Server v%s is starting...\n", Constants.CLIENT_VERSION);

            gameServer = new GameServer();
            gameServer.run();
        } catch (Exception ex) {
            Log.println_e("Server Crashed!");
            Log.println_e(ex.getMessage());

            try {
                Thread.sleep(10000);
                Log.println_e("Server is now restarting...");
                GameServer.main(args);
            } catch (InterruptedException ex1) {
                Log.println_e(ex1.getMessage());
            }
        }
    }
}

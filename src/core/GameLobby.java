package core;

import database.AccessObjectImplementation.UserDAOImpl;
import database.Models.Lobby;
import database.Models.User;
import metadata.Constants;
import utility.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Random;

public class GameLobby implements Runnable {


    private ArrayList<User> users;
    private ArrayList<String> availableCharacters;
    private ArrayList<GameClient> clients;
    private boolean gameStarted;


    private Lobby lobby;

    private ServerSocket serverSocket;
    private boolean done;

    /**
     * @param lobby
     */
    public GameLobby(Lobby lobby) {
        try {
            // create a ServerSocket for the lobby
            serverSocket = new ServerSocket(lobby.getPort());
            this.lobby = lobby;

        } catch (IOException e) {
            Log.println(e.getMessage());
        }
        gameStarted = false;
        availableCharacters = new ArrayList<>(Constants.characters.keySet());
        done = false;
    }

    /**
     *
     */
    public void run() {
        // loop for 4 players
        try {

            // wait for players to join or owner to start game
            while(clients.size() < 4 && gameStarted == false)
                ;

            // game will be running here
            while (!done) {
                // todo: check for game end
            }

            // update user records after the game ends
            for(GameClient client : clients){
                // todo: change user's wins and losses as well as games played
                UserDAOImpl.getDao().updateUser(client.getUser());
                removePlayer(client);
            }

            serverSocket.close();
        } catch (IOException e) {
            Log.println(e.getMessage());
        }
    }

    /**
     * @param serverSocket
     */
    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public void removePlayer(GameClient client)
    {
        //Add the character a client was playing back when they disconnect
        availableCharacters.add(client.getUser().getCharacter());
    }

    /**
     * @param client
     */
    public void addPlayerToLobby(GameClient client) {
        if (clients.size() < 4) {
            try {
                clients.add(client);
                client.getClientSocket().bind(new InetSocketAddress(serverSocket.getLocalPort()));
                lobby.addPlayer(client.getUserID());

                // assign a random character to the player when adding to lobby
                int randomIndex = new Random().nextInt(availableCharacters.size());
                client.getUser().setCharacter(availableCharacters.get(randomIndex));

            } catch (IOException e) {
                Log.println(e.getMessage());
            }
        }
    }

    /**
     * Called at end of game to return clients to main server loop
     */
    public void endGame() {
        try {
            for (GameClient client : clients) {
                // set client sockets to main server socket
                client.getClientSocket().bind(new InetSocketAddress(GameServer.getInstance().getServerSocket().getLocalPort()));

            }
        } catch (IOException e) {
            Log.println(e.getMessage());
        }
        done = true;
    }

    public int getPort(){
        return this.lobby.getPort();
    }


}

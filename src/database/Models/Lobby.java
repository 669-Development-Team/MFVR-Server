/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.Models;

import java.util.ArrayList;

/**
 * @author Travis
 */
public class Lobby {

    private int port;
    private String name;
    private int privacy;
    private boolean passwordRequired;
    private String password;

    // foreign key values -- move these to an array, instead
    //    private int owner;
    //    private int player1;
    //    private int player2;
    //    private int player3;

    // this is to store playerID in a easily accessible form
    private ArrayList<Long> playerList = new ArrayList<>();

    public Lobby() {
    }

    /**
     * @param port
     * @param name
     * @param password
     * @param privacy
     * @param owner
     */
    public Lobby(int port, String name, int privacy,
                 boolean passwordRequired, String password,
                 long owner) {
        this(port, name, privacy, passwordRequired, password, owner, 0, 0, 0);
    }

    public Lobby(int port, String name, int privacy,
                 boolean passwordRequired, String password,
                 long owner, long player1, long player2, long player3) {
        this.port = port;
        this.name = name;
        this.password = password;
        this.privacy = privacy;
        this.passwordRequired = passwordRequired;
        this.playerList.add(owner);
        this.playerList.add(player1);
        this.playerList.add(player2);
        this.playerList.add(player3);
    }

    public int getPort() {
        return this.port;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPrivacy() {
        return this.privacy;
    }

    public long getPlayer(int playerNum) {
        long playerID = 0;
        try {
            playerID = playerList.get(playerNum);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return playerID;
    }

    public long getOwner() {
        return this.playerList.get(0);

    }

    public long getPlayer1() {
        return this.playerList.get(1);
    }

    public long getPlayer2() {
        return this.playerList.get(2);
    }

    public long getPlayer3() {
        return this.playerList.get(3);
    }

    public boolean getPasswordRequired() {
        return this.passwordRequired;
    }

    public void setPasswordRequired(boolean passwordRequired) {
        this.passwordRequired = passwordRequired;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    /**
     * @param playerNum the player you wish to set, 0 for owner, 1-3 for others
     * @param userID    the playerID you wish to add to the object
     * @return
     */
    public void setPlayer(int playerNum, long userID) {
        try {
            playerList.set(playerNum, userID);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void setOwner(long owner) {
        this.playerList.set(0, owner);
    }

    public void setPlayer1(long player) {
        this.playerList.set(1, player);
    }

    public void setPlayer2(long player) {
        this.playerList.set(2, player);
    }

    public void setPlayer3(long player) {
        this.playerList.set(3, player);
    }

    public boolean addPlayer(Long player) {
        if (this.playerList.size() >= 4)
            return false;
        return this.playerList.add(player);
    }

    public boolean removePlayer(Long player) {
        return this.playerList.remove(player);
    }
}
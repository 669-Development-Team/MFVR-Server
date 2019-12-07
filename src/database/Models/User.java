/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.Models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import core.GameClient;

/**
 * @author Travis
 */
public class User {

    private long ID;
    private String userName;
    private String email;
    private String password;
    private Timestamp joined;
    private Timestamp lastOnline;
    private int played;
    private int won;
    private int lost;

    private String character;

    public User() {
    }

    public User(String userName, String email, String password) {
        this(0L, userName, email, password,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()),
                0, 0, 0);
    }

    public User(long ID, String userName, String email, String password,
                Timestamp joined, Timestamp lastOnline,
                int played, int won, int lost) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.joined = joined;
        this.lastOnline = lastOnline;
        this.played = played;
        this.won = won;
        this.lost = lost;
    }

    public long getID() {
        return this.ID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Timestamp getJoined() {
        return this.joined;
    }

    public Timestamp getLastLogin() {
        return this.lastOnline;
    }

    public int getPlayed() {
        return this.played;
    }

    public int getWon() {
        return this.won;
    }

    public int getLost() {
        return this.lost;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJoined(Timestamp joined) {
        this.joined = joined;
    }

    public void setLastOnline(Timestamp lastOnline) {
        this.lastOnline = lastOnline;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public String getCharacter() {
        return this.character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}

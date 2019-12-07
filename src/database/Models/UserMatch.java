/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.Models;

/**
 * @author Travis
 */
public class UserMatch {
    private long ID;
    private int role;
    private int attacksMade;
    private int attacksHit;
    private int timesDowned;
    private int aliveAtEnd;

    //foreign key vaules
    private long matchID;
    private long playerID;

    public UserMatch() {
    }

    public UserMatch(long ID, int role, int attacksMade, int attacksHit,
                     int timesDowned, int aliveAtEnd, long matchID, long playerID) {
        this.ID = ID;
        this.role = role;
        this.attacksMade = attacksMade;
        this.attacksHit = attacksHit;
        this.timesDowned = timesDowned;
        this.aliveAtEnd = aliveAtEnd;
        this.matchID = matchID;
        this.playerID = playerID;
    }

    public long getID() {
        return this.ID;
    }

    public int getRole() {
        return this.role;
    }

    public int getAttacksMade() {
        return this.attacksMade;
    }

    public int getAttacksHit() {
        return this.attacksHit;
    }

    public int getTimesDowned() {
        return this.timesDowned;
    }

    public int getAliveAtEnd() {
        return this.aliveAtEnd;
    }

    public long getMatchID() {
        return this.matchID;
    }

    public long getPlayerID() {
        return this.playerID;
    }

    public long setID(long id) {
        return this.ID = id;
    }

    public int setRole(int role) {
        return this.role = role;
    }

    public int setAttacksMade(int attacksMade) {
        return this.attacksMade = attacksMade;
    }

    public int setAttacksHit(int attacksHit) {
        return this.attacksHit = attacksHit;
    }

    public int setTimesDowned(int timesDowned) {
        return this.timesDowned = timesDowned;
    }

    public int setAliveAtEnd(int aliveAtEnd) {
        return this.aliveAtEnd = aliveAtEnd;
    }

    public long setMatchID(long matchID) {
        return this.matchID = matchID;
    }

    public long setPlayerID(long playerID) {
        return this.playerID = playerID;
    }
}   

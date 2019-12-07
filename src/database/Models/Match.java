/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.Models;

/**
 *
 * @author Travis
 */
public class Match {

    private long ID;
    private int endState;
    private int endTime;

    // foreign key values
    private long monster;
    private long survivor1;
    private long survivor2;
    private long survivor3;

    public Match() {
    }

    public Match(long ID, int endState, int endTime,
                 long monster, long survivor1, long survivor2, long survivor3) {
        this.ID = ID;
        this.endState = endState;
        this.endTime = endTime;
        this.monster = monster;
        this.survivor1 = survivor1;
        this.survivor2 = survivor2;
        this.survivor3 = survivor3;
    }

    public long getID() {
        return this.ID;
    }

    public int getEndState() {
        return this.endState;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public long getMonster() {
        return this.monster;
    }

    public long getSurvivor1() {
        return this.survivor1;
    }

    public long getSurvivor2() {
        return this.survivor2;
    }

    public long getSurvivor3() {
        return this.survivor3;
    }

    public long setID(long ID) {
        return this.ID = ID;
    }

    public int setEndState(int endState) {
        return this.endState = endState;
    }

    public int setEndTime(int endTime) {
        return this.endTime = endTime;
    }

    public long setMonster(long monster) {
        return this.monster = monster;
    }

    public long setSurvivor1(long survivor) {
        return this.survivor1 = survivor;
    }

    public long setSurvivor2(long survivor) {
        return this.survivor2 = survivor;
    }

    public long setSurvivor3(long survivor) {
        return this.survivor3 = survivor;
    }
}

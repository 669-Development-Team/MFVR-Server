/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjectImplementation;

import database.AccessObjects.UserMatchDAO;
import database.DCM;
import database.Models.Match;
import database.Models.UserMatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Travis
 */
public class UserMatchDAOImpl implements UserMatchDAO {
    private static UserMatchDAO userMatchDAO;
    public static UserMatchDAO getDao(){
        if(userMatchDAO == null)
            userMatchDAO = new UserMatchDAOImpl();
        return userMatchDAO;
    }

    @Override
    public UserMatch getUserMatchByID(long ID) {
        String query = "SELECT * FROM match WHERE ID = ?";

        UserMatch userMatch = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, ID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int role = rs.getInt("role");
                int attacksMade = rs.getInt("attacksMade");
                int attacksHit = rs.getInt("attacksHit");
                int timesDowned = rs.getInt("timesDowned");
                int aliveAtEnd = rs.getInt("aliveAtEnd");
                long matchID = rs.getLong("matchID");
                long playerID = rs.getLong("playerID");
                userMatch = new UserMatch(ID, role, attacksMade, attacksHit,
                        timesDowned, aliveAtEnd, matchID, playerID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userMatch;
    }

    @Override
    public List<UserMatch> getAllUserMatchesByUserID(long userID) {
        String query = "SELECT * FROM match WHERE PlayerID = ?";

        List<UserMatch> userMatchList = new ArrayList<>();
        UserMatch userMatch = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                long ID = rs.getLong("ID");
                int role = rs.getInt("role");
                int attacksMade = rs.getInt("attacksMade");
                int attacksHit = rs.getInt("attacksHit");
                int timesDowned = rs.getInt("timesDowned");
                int aliveAtEnd = rs.getInt("aliveAtEnd");
                long matchID = rs.getLong("matchID");
                userMatch = new UserMatch(ID, role, attacksMade, attacksHit,
                        timesDowned, aliveAtEnd, matchID, userID);
                userMatchList.add(userMatch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userMatchList;
    }

    @Override
    public boolean createUserMatch(UserMatch userMatch){
        boolean success = false;

        String query = "INSERT INTO UserMatch(role, attacksMade, attacksHit, timesDowned," +
                "aliveAtEnd, MatchID, PlayerID) VALUES(?,?,?,?,?,?,?)";

        try {
            Connection connection = DCM.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userMatch.getRole());
            ps.setInt(2, userMatch.getAttacksMade());
            ps.setInt(3, userMatch.getAttacksHit());
            ps.setInt(4, userMatch.getTimesDowned());
            ps.setInt(5, userMatch.getAliveAtEnd());
            ps.setLong(6, userMatch.getMatchID());
            ps.setLong(7, userMatch.getPlayerID());
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}

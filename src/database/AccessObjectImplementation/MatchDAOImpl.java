/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjectImplementation;

import database.AccessObjects.MatchDAO;
import database.DCM;
import database.Models.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Travis
 */
public class MatchDAOImpl implements MatchDAO {
    private static MatchDAO matchDAO;
    public static MatchDAO getDao(){
        if(matchDAO == null)
            matchDAO = new MatchDAOImpl();
        return matchDAO;
    }

    @Override
    public Match getMatchByID(long ID) {
        String query = "SELECT * FROM match WHERE ID = ?";

        Match match = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, ID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int endState = rs.getInt("endState");
                int endTime = rs.getInt("endTime");
                long monster = rs.getLong("monster");
                long survivor1 = rs.getLong("Survivor1");
                long survivor2 = rs.getLong("Survivor2");
                long survivor3 = rs.getLong("Survivor3");
                match = new Match(ID, endState, endTime,
                        monster, survivor1, survivor2, survivor3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }

    @Override
    public List<Match> getMatchesByUserID(long userID) {
        String query = "SELECT * FROM match WHERE (monster = ? OR Survivor1 = ? OR Survivor2 = ? OR Survivor3 = ?)";

        List<Match> matchList = new ArrayList<>();
        Match match = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, userID);
            ps.setLong(2, userID);
            ps.setLong(3, userID);
            ps.setLong(4, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                long ID = rs.getLong("id");
                int endState = rs.getInt("endState");
                int endTime = rs.getInt("endTime");
                long monster = rs.getLong("monster");
                long survivor1 = rs.getLong("Survivor1");
                long survivor2 = rs.getLong("Survivor2");
                long survivor3 = rs.getLong("Survivor3");
                match = new Match(ID, endState, endTime,
                        monster, survivor1, survivor2, survivor3);
                matchList.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchList;
    }

    @Override
    public boolean createMatch(Match match){
        boolean success = false;

        String query = "INSERT INTO Match(endState, endTime, monster," +
                "Survivor1, Survivor2, Survivor3) VALUES(?,?,?,?,?,?,?)";

        try {
            Connection connection = DCM.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, match.getEndState());
            ps.setInt(2, match.getEndTime());
            ps.setLong(3, match.getMonster());
            ps.setLong(4, match.getSurvivor1());
            ps.setLong(5, match.getSurvivor2());
            ps.setLong(6, match.getSurvivor3());
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}

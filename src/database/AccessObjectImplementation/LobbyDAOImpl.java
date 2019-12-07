/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjectImplementation;

import database.AccessObjects.LobbyDAO;
import database.DCM;
import database.Models.Lobby;
import database.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Travis
 */
public class LobbyDAOImpl implements LobbyDAO {

    private static LobbyDAO lobbyDAO;
    public static LobbyDAO getDao(){
        if(lobbyDAO == null)
            lobbyDAO = new LobbyDAOImpl();
        return lobbyDAO;
    }

    @Override
    public List<Lobby> getAllLobbies() {
        String query = "SELECT * FROM lobby";

        List<Lobby> lobbyList = new ArrayList<>();
        Lobby lobby = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                String password = rs.getString("password");
                boolean passwordRequired = rs.getBoolean("passwordRequired");
                int privacy = rs.getInt("privacy");
                long owner = rs.getLong("owner");
                long player1 = rs.getLong("player1");
                long player2 = rs.getLong("player2");
                long player3 = rs.getLong("player3");
                lobby = new Lobby(ID, name,  privacy, passwordRequired, password, owner, player1, player2, player3);
                lobbyList.add(lobby);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lobbyList;
    }

    @Override
    public Lobby getLobbyByID(int ID) {
        String query = "SELECT * FROM lobby WHERE ID = ?";

        Lobby lobby = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, ID);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                boolean passwordRequired = rs.getBoolean("passwordRequired");
                int privacy = rs.getInt("privacy");
                long owner = rs.getLong("owner");
                long player1 = rs.getLong("player1");
                long player2 = rs.getLong("player2");
                long player3 = rs.getLong("player3");
                lobby = new Lobby(ID, name,  privacy, passwordRequired, password, owner, player1, player2, player3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lobby;
    }

    @Override
    public List<Lobby> getLobbiesByName(String name) {
        String query = "SELECT * FROM lobby WHERE name = ?";

        Lobby lobby = null;
        List<Lobby> lobbyList = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String password = rs.getString("password");
                boolean passwordRequired = rs.getBoolean("passwordRequired");
                int privacy = rs.getInt("privacy");
                long owner = rs.getLong("owner");
                long player1 = rs.getLong("player1");
                long player2 = rs.getLong("player2");
                long player3 = rs.getLong("player3");
                lobby = new Lobby(ID, name,  privacy, passwordRequired, password, owner, player1, player2, player3);
                lobbyList.add(lobby);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lobbyList;
    }

    @Override
    public Lobby getLobbyByOwner(User owner) {
        String query = "SELECT * FROM lobby WHERE owner = ?";

        Lobby lobby = null;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, owner.getID());

            rs = ps.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String name = rs.getString("name");
                boolean passwordRequired = rs.getBoolean("passwordRequired");
                String password = rs.getString("password");
                int privacy = rs.getInt("privacy");
                long player1 = rs.getLong("player1");
                long player2 = rs.getLong("player2");
                long player3 = rs.getLong("player3");
                lobby = new Lobby(ID, name,  privacy, passwordRequired, password, owner.getID(), player1, player2, player3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lobby;
    }

    @Override
    public boolean createLobby(Lobby lobby, Long userID) {
        boolean success = false;
        String query = "INSERT INTO Lobby(ID, name, privacy, passwordRequired, password, owner) VALUES(?,?,?,?,?,?)";

        try {
            Connection connection = DCM.getDataSource().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, lobby.getPort());
            ps.setString(2, lobby.getName());
            ps.setInt(3, lobby.getPrivacy());
            ps.setBoolean(4, lobby.getPasswordRequired());
            ps.setString(5, lobby.getPassword());
            ps.setLong(6, userID);
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean updateLobbyPlayers(Lobby lobby) {
        boolean success = false;
        String query = "UPDATE Lobby SET `owner` = ? `player1` = ? `player2` = ? `player3` = ? WHERE `ID` = ?";

        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, lobby.getOwner());
            ps.setLong(2, lobby.getPlayer1());
            ps.setLong(3, lobby.getPlayer2());
            ps.setLong(4, lobby.getPlayer3());
            ps.setLong(5, lobby.getPort());
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean deleteLobby(Lobby lobby) {
        boolean success = false;
        String query = "DELETE FROM Lobby WHERE ID = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DCM.getDataSource().getConnection();
            ps = connection.prepareStatement(query);
            ps.setLong(1, lobby.getPort());
            ps.executeUpdate();
            ps.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}

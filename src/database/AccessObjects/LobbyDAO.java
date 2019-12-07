/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjects;

import database.Models.Lobby;
import database.Models.User;

import java.util.List;

/**
 *
 * @author Travis
 */
public interface LobbyDAO {
    /**
     *
     * @param ID
     * @return
     */
    public Lobby getLobbyByID(int ID);

    /**
     *
     * @return
     */
    public List<Lobby> getAllLobbies();

    /**
     *
     * @param name
     * @return
     */
    public List<Lobby> getLobbiesByName(String name);

    /**
     *
     * @param owner
     * @return
     */
    public Lobby getLobbyByOwner(User owner);

    /**
     *
     * @param lobby
     * @param userID
     * @return
     */
    public boolean createLobby(Lobby lobby, Long userID);

    /**
     * Update an existing lobby, returning true on success, and false on failure
     *
     * @param lobby The lobby to update
     * @return true on success, false otherwise
     */
    public boolean updateLobbyPlayers(Lobby lobby);


    /**
     *
     * @param lobby
     * @return
     */
    public boolean deleteLobby(Lobby lobby);

}

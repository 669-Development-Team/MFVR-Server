/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjects;

import database.Models.UserMatch;

import java.util.List;

/**
 * @author Travis
 */
public interface UserMatchDAO {
    /**
     *
     * @param ID
     * @return
     */
    public UserMatch getUserMatchByID(long ID);

    /**
     *
     * @param ID
     * @return
     */
    public List<UserMatch> getAllUserMatchesByUserID(long ID);

    public boolean createUserMatch(UserMatch userMatch);
}

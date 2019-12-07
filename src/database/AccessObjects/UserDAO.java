/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.AccessObjects;

import database.Models.User;

import java.util.List;
import java.util.Map;

/**
 * @author Travis
 */
public interface UserDAO {

    /**
     *
     * @return List containing all users
     */
    public List<User> getAllUsers();

    /**
     * @param ID User ID to look up name for
     * @return name of User
     */
    public String getUserNameByID(long ID);

    /**
     *
     * @return A map with all usernames associated with ID
     */
    public Map<Long,String> getAllUsernames();

    /**
     * @param ID
     * @return
     */
    public User getUserByID(long ID);

    /**
     * Validates User Credentials exist in database, and returns the relevant User
     * object, or null, if the User credentials don't match a database entry
     *
     * @param loginName A username or email
     * @param password
     * @return Valid User object, or null, if no User is found
     */
    public User validateUserCredentials(String loginName, String password);

    /**
     * @param user
     * @return
     */
    public User createUser(User user);

    /**
     * Updates LastOnline, Played, Won, and Lost values for the selected User in the Database
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user);

    /**
     *
     * @param user
     * @param newPassword
     * @return
     */
    public boolean updateUserPassword(User user, String oldPassword, String newPassword);

    /**
     * Verify that the given UserName is not being used by an existing account
     * @param username
     * @return
     */
    public boolean verifyUniqueUsername(String username);

    /**
     * Verify that the given email is not being used by an existing account
     * @param email
     * @return
     */
    public boolean verifyUniqueEmail(String email);
}

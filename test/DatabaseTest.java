package test;

import configuration.GameServerConf;
import database.AccessObjectImplementation.LobbyDAOImpl;
import database.AccessObjectImplementation.MatchDAOImpl;
import database.AccessObjectImplementation.UserDAOImpl;
import database.AccessObjectImplementation.UserMatchDAOImpl;
import database.AccessObjects.LobbyDAO;
import database.AccessObjects.MatchDAO;
import database.AccessObjects.UserDAO;
import database.AccessObjects.UserMatchDAO;
import database.DCM;
import database.Models.Lobby;
import database.Models.Match;
import database.Models.User;
import database.Models.UserMatch;
import utility.Log;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;


public class DatabaseTest {
    // Singleton Instance
    // Server Variables
    private boolean isDone; // Server Loop Flag
    private GameServerConf configuration; // Stores server config. variables
    private ServerSocket serverSocket;
    private ExecutorService clientThreadPool;
    // Reference Tables

    public static void runUserTest(){
        UserTest uTest = new UserTest();
        System.out.println("Test: CreateUser result is: " + uTest.testCreate());
        System.out.println("Test: GetUserByID result is: " + uTest.testGetUserByID());
        System.out.println("Test: UpdateUser result is: " + uTest.testUpdate());
        System.out.println("Test: UpdateUserPassword result is: " + uTest.testUpdatePassword());
        System.out.println("Test: ValidateCredentials result is: " + uTest.testValidateCredentials());

    }

    public static void runLobbyTest(){

    }

    public static void main(String [] ars){
        Lobby lobby;
        User user;
        Match match;
        UserMatch uMatch;

        UserDAO userDAO = new UserDAOImpl();
        LobbyDAO lobbyDAO = new LobbyDAOImpl();
        MatchDAO matchDAO = new MatchDAOImpl();
        UserMatchDAO userMatchDAO = new UserMatchDAOImpl();


        if (DCM.getInstance() == null) {
            Log.println_e("Database Connection Failed!");
            System.exit(-1);
        }

        runUserTest();
    }



}

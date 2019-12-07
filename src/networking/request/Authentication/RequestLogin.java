package networking.request.Authentication;

// Java Imports

import core.GameClient;
import core.GameServer;
import database.AccessObjectImplementation.UserDAOImpl;
import database.AccessObjects.UserDAO;
import database.Models.User;
import metadata.Constants;
import networking.request.GameRequest;
import networking.response.Authentication.ResponseLogin;
import utility.DataReader;
import utility.Log;

import java.io.IOException;

// Other Imports

/**
 * The RequestLogin class authenticates the user information to log in. Other
 * tasks as part of the login process lies here as well.
 */
public class RequestLogin extends GameRequest {

    // Data
    private String version;
    private String user_id;
    private String password;
    // Responses
    private ResponseLogin responseLogin;


    private UserDAO dao = new UserDAOImpl();

    public RequestLogin() {
        responses.add(responseLogin = new ResponseLogin());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        user_id = DataReader.readString(dataInput).trim();
        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        Log.printf("User '%s' is connecting...", user_id);
        User user = null;
        // Checks if the connecting client meets the minimum version required
        if (version.compareTo(Constants.CLIENT_VERSION) >= 0) {
            if (!user_id.isEmpty()) {
                // Verification Needed
                user = dao.validateUserCredentials(user_id, password);
            }
            if (user == null) {
                responseLogin.setStatus((short) 1); // User info is incorrect
                Log.printf("User '%s' has failed to log in.", user_id);
            } else {
                if (client.getUser() == null || user.getID() != client.getUser().getID()) {
                    GameClient thread = GameServer.getInstance().getThreadByUserID(user.getID());
                    // If account is already in use, remove and disconnect the client
                    if (thread != null) {
                        responseLogin.setStatus((short) 2); // Account is in use
//                        thread.removeUserData();
//                        thread.newSession();
                        
                        Log.printf("User '%s' account is already in use.", user_id);
                    } else {
                        // Continue with the login process
                        GameServer.getInstance().setActivePlayer(user);
                        // Pass Player reference into thread
                        client.setUser(user);
                        // Set response information
                        responseLogin.setStatus((short) 0); // Login is a success
                        responseLogin.setUser(user);
                        Log.printf("User '%s' has successfully logged in.", user.getUserName());
                    }
                }
            }
        } else {
            responseLogin.setStatus((short) 3); // Client version not compatible
            Log.printf("User '%s' has failed to log in. (v%s)", user_id, version);
        }
    }
}

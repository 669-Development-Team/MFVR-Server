package networking.request.Authentication;

import database.AccessObjectImplementation.UserDAOImpl;
import database.AccessObjects.UserDAO;
import database.Models.User;
import metadata.Constants;
import networking.request.GameRequest;
import networking.response.Authentication.ResponseRegister;
import utility.DataReader;
import utility.Log;

import java.io.IOException;

public class RequestRegister extends GameRequest {


    private ResponseRegister responseRegister;
    private String version;
    private String userName;
    private String email;
    private String password;

    private UserDAO dao = new UserDAOImpl();

    public RequestRegister() {
        responses.add(responseRegister = new ResponseRegister());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        userName = DataReader.readString(dataInput).trim();
        email = DataReader.readString(dataInput).trim();
        password = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        if (version.compareTo(Constants.CLIENT_VERSION) >= 0) {
            if (!dao.verifyUniqueEmail(email)) {
                Log.println("Email: '" + email + "' is already being used");
                responseRegister.setStatus((short) 1);
            } else if (!dao.verifyUniqueUsername(userName)) {
                Log.println("Username: '" + userName + "' is already being used");
                responseRegister.setStatus((short) 2);
            } else {
                /* TODO: do some password verification here
                 * if password doesn't meet minimum standard,
                 * responseRegister.setStats((short)3);
                 */
                responseRegister.setStatus((short) 0);
                User user = dao.createUser(new User(userName, email, password));
                responseRegister.setUser(user);
            }
            Log.println("New User Created!");
        } else {
            responseRegister.setStatus((short) 4); // Client version not compatible
            Log.printf("User '%s' has failed to register. (v%s)", email, version);
        }
    }
}
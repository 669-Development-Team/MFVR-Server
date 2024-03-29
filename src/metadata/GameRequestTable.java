package metadata;

// Java Imports
import java.util.HashMap;
import java.util.Map;

// Other Imports
import networking.request.GameRequest;
import utility.Log;

/**
 * The GameRequestTable class stores a mapping of unique request code numbers
 * with its corresponding request class.
 */
public class GameRequestTable {

    private static Map<Short, Class> requestTable = new HashMap<Short, Class>(); // Request Code -> Class

    /**
     * Initialize the hash map by populating it with request codes and classes.
     */
    public static void init() {
        // Populate the table using request codes and class names
        add(Constants.CMSG_HEARTBEAT, "RequestHeartbeat");
        add(Constants.CMSG_PUSHUPDATE, "RequestPushUpdate");
        add(Constants.CMSG_KEEPALIVE, "RequestKeepAlive");

        add(Constants.CMSG_REGISTER, "RequestRegister");
        add(Constants.CMSG_LOGIN, "RequestLogin");

        add(Constants.CMSG_STARTGAME, "RequestStartGame");
        add(Constants.CMSG_JOINGAME, "RequestJoinGame");

        add(Constants.CMSG_PICKUP, "RequestPickup");
        add(Constants.CMSG_HIT, "RequestHit");
    }

    /**
     * Map the request code number with its corresponding request class, derived
     * from its class name using reflection, by inserting the pair into the
     * table.
     *
     * @param code a value that uniquely identifies the request type
     * @param name a string value that holds the name of the request class
     */
    public static void add(short code, String name) {
        try {
            int secondDigit = (code/10) % 10;
            String classLocation;

            switch (secondDigit) {
                case 1:
                    classLocation = "networking.request.Authentication.";
                    break;
                case 2:
                    classLocation = "networking.request.Lobby.";
                    break;
                default:
                    classLocation = "networking.request.";
            }

            requestTable.put(code, Class.forName(classLocation + name));
        } catch (ClassNotFoundException e) {
            Log.println_e(e.getMessage());
        }
    }

    /**
     * Get the instance of the request class by the given request code.
     *
     * @param request_code a value that uniquely identifies the request type
     * @return the instance of the request class
     */
    public static GameRequest get(short request_code) {
        GameRequest request = null;

        try {
            Class name = requestTable.get(request_code);

            if (name != null) {
                request = (GameRequest) name.newInstance();
                request.setID(request_code);
            } else {
                Log.printf_e("Request Code [%d] does not exist!\n", request_code);
            }
        } catch (Exception e) {
            Log.println_e(e.getMessage());
        }

        return request;
    }
}

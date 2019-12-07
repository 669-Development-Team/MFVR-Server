package core;

// Other Imports

import database.Models.User;
import networking.response.GameResponse;
import utility.Log;

public class NetworkManager {

    private NetworkManager() {
    }

    /**
     * Push a pending response to a user's queue.
     *
     * @param userID holds the player ID
     * @param response is the instance containing the response information
     */
    public static void addResponseForUser(long userID, GameResponse response) {
        GameClient client = GameServer.getInstance().getThreadByUserID(userID);

        if (client != null) {
            client.addResponseForUpdate(response);
        } else {
            Log.printf_e("Failed to create response for user, %d.", userID);
        }
    }

    /**
     * Push a pending response to all users' queue except one user.
     *
     * @param player_id holds the excluding player ID
     * @param response is the instance containing the response information
     */
    public static void addResponseForAllOnlinePlayers(int player_id, GameResponse response) {
        for (GameClient client : GameServer.getInstance().getActiveThreads().values()) {
            User user = client.getUser();

            if (user != null && client.getUser().getID() != player_id) {
                client.addResponseForUpdate(response);
            }
        }
    }
}

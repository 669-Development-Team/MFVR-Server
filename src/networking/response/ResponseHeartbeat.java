package networking.response;

import core.GameServer;
import database.Models.User;
import metadata.Constants;
import utility.GamePacket;
import java.util.Vector;

public class ResponseHeartbeat extends GameResponse {

    private User user;

    public ResponseHeartbeat() {
        responseCode = Constants.SMSG_HEARTBEAT;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);

        //Add the update number
        packet.addShort16((short)GameServer.getInstance().getThreadByUserID(user.getID()).getUpdateNumber());

        //A list of players that have an update
        Vector<User> usersWithUpdates = new Vector<>();

        //Add the player's update
        for(User user : GameServer.getInstance().getActivePlayers()) {
            if(user != this.user)
                packet.addBytes(GameServer.getInstance().getThreadByUserID(user.getID()).getLatestUpdateFromClient());
        }

        //Send the number of serverUpdates
        packet.addShort16((short)usersWithUpdates.size());

        return packet.getBytes();
    }
}
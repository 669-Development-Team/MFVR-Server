package networking.response.Authentication;

// Other Imports

import database.Models.User;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

/**
 * The ResponseLogin class contains information about the authentication
 * process.
 */
public class ResponseLogin extends GameResponse {

    private short status;
    private User user;

    public ResponseLogin() {
        responseCode = Constants.SMSG_LOGIN;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if (status == 0) {
            packet.addLong(user.getID());
            packet.addString(user.getUserName());
            packet.addString(user.getEmail());
            packet.addInt32(user.getPlayed());
            packet.addInt32(user.getWon());
            packet.addInt32(user.getLost());
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
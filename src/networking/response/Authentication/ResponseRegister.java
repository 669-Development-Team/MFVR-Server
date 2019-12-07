package networking.response.Authentication;

import database.Models.User;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseRegister extends GameResponse {

    private short status;
    private User user;

    public ResponseRegister() {
        responseCode = Constants.SMSG_REGISTER;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if (status == 0) {
            packet.addLong(user.getID());
            packet.addString(user.getUserName());
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setUser(User user){
        this.user = user;
    }
}

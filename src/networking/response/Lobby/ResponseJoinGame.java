package networking.response.Lobby;

import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseJoinGame extends GameResponse {
    public ResponseJoinGame() {
        responseCode = Constants.SMSG_JOINGAME;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        return packet.getBytes();
    }
}

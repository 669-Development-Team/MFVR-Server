package networking.response.Lobby;

import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseStartGame extends GameResponse {

    public ResponseStartGame() {
        responseCode = Constants.SMSG_STARTGAME;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        return packet.getBytes();
    }
}

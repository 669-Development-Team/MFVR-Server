package networking.response.Lobby;

import core.GameServer;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseEndGame extends GameResponse {
    short status;

    public ResponseEndGame() {
        responseCode = Constants.SMSG_ENDGAME;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if(status == 0){
            // add main server port to packet
            packet.addInt32(GameServer.getInstance().getServerSocket().getLocalPort());
        }
        return packet.getBytes();
    }

    public void setStatus(short status){this.status = status;}
}

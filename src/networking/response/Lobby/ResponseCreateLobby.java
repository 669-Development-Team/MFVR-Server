package networking.response.Lobby;

import database.Models.Lobby;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseCreateLobby extends GameResponse {

    private short status;
    private Lobby lobby;

    public ResponseCreateLobby() {
        responseCode = Constants.SMSG_CREATELOBBY;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if(status == 0){
            packet.addInt32(lobby.getPort());
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

}

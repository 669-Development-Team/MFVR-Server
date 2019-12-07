package networking.response.Lobby;

import database.Models.Lobby;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseJoinLobby extends GameResponse {

    public ResponseJoinLobby() {
        responseCode = Constants.SMSG_JOINLOBBY;
    }

    private short status;
    private long lobbyID;
    private int port;

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);
        if (status == 0) {
            packet.addLong(lobbyID);
            packet.addInt32(port);
        }
        return packet.getBytes();
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setLobbyID(long lobbyID) {
        this.lobbyID = lobbyID;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
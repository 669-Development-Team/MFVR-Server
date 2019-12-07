package networking.response.Lobby;

import database.Models.Lobby;
import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

import java.util.List;
import java.util.Map;

public class ResponseGetLobbies extends GameResponse {

    public ResponseGetLobbies() {
        responseCode = Constants.SMSG_GETLOBBIES;
    }

    short status;
    int size;
    List<Lobby> lobbyList;
    Map<Long,String> userNames;
    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(status);

        if(status == 0) {
            Lobby lobby = null;
            packet.addInt32(size);
            for (int i = 0; i < lobbyList.size(); i++) {
                lobby = lobbyList.get(i);
                packet.addLong(lobby.getPort());
                packet.addString(lobby.getName());
                packet.addString(userNames.get(lobby.getOwner()));
                packet.addInt32(lobby.getPrivacy());
                packet.addBoolean(lobby.getPasswordRequired());
            }
        }
        return packet.getBytes();
    }

    public void setStatus(short status){this.status = status;}

    public void setLobbyList(List<Lobby> lobbyList){ this.lobbyList.addAll(lobbyList);}

    public void setUserNames(Map<Long,String> userNames){ this.userNames = userNames;}
}

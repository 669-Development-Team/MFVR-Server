package networking.request.Lobby;

import core.GameLobby;
import core.GameServer;
import database.AccessObjectImplementation.LobbyDAOImpl;
import database.Models.Lobby;
import metadata.Constants;
import networking.request.GameRequest;
import networking.response.Lobby.ResponseJoinLobby;
import utility.DataReader;
import utility.Log;

import java.io.IOException;

public class RequestJoinLobby extends GameRequest {

    private ResponseJoinLobby responseJoinLobby;

    private String version;
    private int lobbyID;
    private long playerID;

    public RequestJoinLobby() {
        this.responses.add(responseJoinLobby = new ResponseJoinLobby());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        lobbyID = DataReader.readInt(dataInput);
        playerID = DataReader.readLong(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        Lobby lobby = LobbyDAOImpl.getDao().getLobbyByID(lobbyID);
        responseJoinLobby.setLobbyID(lobbyID);

        if (version.compareTo(Constants.CLIENT_VERSION) >= 0) {
            // try to add player to the selected lobby
            if (lobby.addPlayer(playerID)) {
                Log.println("Player " + playerID + " joined lobby " + lobby.getPort());
                responseJoinLobby.setStatus((short) 0);
                LobbyDAOImpl.getDao().updateLobbyPlayers(lobby);

                // retrieve GameLobbv and add player to it
                GameLobby gameLobby = GameServer.getInstance().getGameLobbyByID(lobby.getPort());
                gameLobby.addPlayerToLobby(GameServer.getInstance().getThreadByUserID(playerID));
                responseJoinLobby.setPort(gameLobby.getPort());
            } else {
                // lobby was full
                responseJoinLobby.setStatus((short) 1);
                Log.println("Lobby full");
            }
        }
    }
}
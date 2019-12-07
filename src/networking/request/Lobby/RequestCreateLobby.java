package networking.request.Lobby;

import core.GameClient;
import core.GameLobby;
import core.GameServer;
import database.AccessObjectImplementation.LobbyDAOImpl;
import database.Models.Lobby;
import networking.request.GameRequest;
import networking.response.Lobby.ResponseCreateLobby;
import utility.DataReader;
import utility.Log;

import java.io.IOException;

public class RequestCreateLobby extends GameRequest {

    private ResponseCreateLobby responseCreateLobby;

    private short status;
    private String version;


    private int port;
    private String name;
    private String password;
    private int privacy;
    private boolean passwordRequired;
    private long owner;

    public RequestCreateLobby() {
        responses.add(responseCreateLobby = new ResponseCreateLobby());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        name = DataReader.readString(dataInput).trim();
        privacy = DataReader.readInt(dataInput);
        passwordRequired = DataReader.readBoolean(dataInput);
        password = DataReader.readString(dataInput).trim();
        owner = DataReader.readLong(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        port = GameServer.getInstance().getFreePort();
        Lobby lobby = new Lobby(port, name, privacy, passwordRequired, password, owner);

        if (LobbyDAOImpl.getDao().createLobby(lobby, owner)) {
            responseCreateLobby.setStatus((short) 0);

            GameClient client = GameServer.getInstance().getThreadByUserID(owner);
            GameLobby gameLobby = new GameLobby(lobby);
            responseCreateLobby.setLobby(lobby);
            Log.println("Lobby creation successful");
        } else {
            responseCreateLobby.setStatus((short) 1);
            Log.println("Lobby creation failed");
        }

    }
}
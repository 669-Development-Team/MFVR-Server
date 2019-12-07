package networking.request.Lobby;

import java.io.IOException;

import core.GameServer;
import networking.request.GameRequest;
import networking.response.Lobby.ResponseJoinGame;
import utility.DataReader;
import utility.Log;

public class RequestJoinGame extends GameRequest {

    private ResponseJoinGame responseJoinGame;
    private String version;

    public RequestJoinGame() {
        responseJoinGame = new ResponseJoinGame();
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        if(!client.inGame()) {
            Log.printf("User '%s' joined the game.", client.getUser().getUserName());
            client.setInGame(true);

            responses.add(responseJoinGame);
        }
    }
}
package networking.request.Lobby;

import java.io.IOException;

import networking.request.GameRequest;
import networking.response.Lobby.ResponseStartGame;
import utility.DataReader;
import utility.Log;

public class RequestStartGame extends GameRequest {

    private ResponseStartGame responseStartGame;
    private String version;

    public RequestStartGame() {
        responses.add(responseStartGame = new ResponseStartGame());
    }

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
    }


    @Override
    public void doBusiness() throws Exception {
        Log.printf("User '%s' started the game.", client.getUser().getUserName());
    }
}
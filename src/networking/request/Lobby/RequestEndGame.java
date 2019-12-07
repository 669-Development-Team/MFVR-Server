package networking.request.Lobby;

import networking.request.GameRequest;
import networking.response.Lobby.ResponseEndGame;
import networking.response.Lobby.ResponseJoinGame;
import utility.DataReader;

import javax.xml.crypto.Data;
import java.io.IOException;

public class RequestEndGame extends GameRequest {

    private ResponseEndGame responseEndGame;
    private String version;
    private long lobbyID;

    public RequestEndGame() { this.responses.add(responseEndGame = new ResponseEndGame());}

    @Override
    public void parse() throws IOException {
        version = DataReader.readString(dataInput).trim();
        lobbyID = DataReader.readLong(dataInput);
    }

    @Override
    public void doBusiness(){

    }
}

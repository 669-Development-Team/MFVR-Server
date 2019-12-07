package networking.request;

import java.io.IOException;
import java.util.Queue;

import networking.response.GameResponse;
import networking.response.ResponseHeartbeat;
import utility.Log;

public class RequestHeartbeat extends GameRequest {

    private ResponseHeartbeat responseHeartbeat;

    public RequestHeartbeat() {
        responses.add(responseHeartbeat = new ResponseHeartbeat());
    }

    @Override
    public void parse() throws IOException {
    }

    @Override
    public void doBusiness() throws Exception {
        //Send the heartbeat response
        responseHeartbeat.setUser(this.client.getUser());
        client.send(responseHeartbeat);

        //Send the other updates for the user
        Queue<GameResponse> responseList = client.getUpdatesForClient();

        for(GameResponse response : responseList)
            client.send(response);
    }
}
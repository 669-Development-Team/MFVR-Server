package networking.request;

import networking.response.GameResponse;
import networking.response.ResponseHeartbeat;

import java.io.IOException;
import java.util.Queue;

public class RequestKeepAlive extends GameRequest {
    public RequestKeepAlive() {

    }

    @Override
    public void parse() throws IOException {
    }

    @Override
    public void doBusiness() throws Exception {
        // this is just here to receive a client request and keep the connection alive
    }
}

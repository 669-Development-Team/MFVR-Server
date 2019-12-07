package networking.request;

import java.io.IOException;
import utility.Log;

public class RequestPushUpdate extends GameRequest {

    private byte[] update;

    public RequestPushUpdate() {
    }

    @Override
    public void parse() throws IOException {
        //Allocate enough memory in update to hold all of the data
        update = new byte[dataInput.available()];
        //Populate it with the data
        dataInput.readFully(update);
    }

    @Override
    public void doBusiness() throws Exception {
        // Replace the player's old update with the new one
        client.setLatestUpdateFromClient(update);
    }
}
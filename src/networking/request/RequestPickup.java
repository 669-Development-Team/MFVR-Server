package networking.request;

import java.io.DataInputStream;
import java.io.IOException;

import core.GameClient;
import core.GameServer;
import networking.response.ResponsePickup;
import utility.DataReader;

public class RequestPickup extends GameRequest {

    private ResponsePickup responsePickup;
    private String item;

    public RequestPickup() {
        responsePickup = new ResponsePickup();
    }

    @Override
    public void parse() throws IOException {
        item = DataReader.readString(dataInput).trim();
    }

    @Override
    public void doBusiness() throws Exception {
        System.out.println(this.client.getUser().getUserName() + " picked up " + item);

        //Set the item name of the response object
        responsePickup.setItem(item);

        //Add the response to every other player's update queue
        for(GameClient player : GameServer.getInstance().getActiveThreads().values())
            if(player != this.client)
                player.addResponseForUpdate(responsePickup);
    }
}
package networking.request;

import core.GameClient;
import core.GameServer;
import metadata.Constants;
import networking.response.ResponseHit;
import utility.DataReader;

import javax.xml.crypto.Data;
import java.io.IOException;

public class RequestHit extends GameRequest {

    private ResponseHit responsehit;
    private short damage;

    public RequestHit() {
        responsehit = new ResponseHit();
    }

    @Override
    public void parse() throws IOException {
        //Get the amount of damage dealt
        damage = DataReader.readShort(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        System.out.println(this.client.getUser().getUserName() + " hit their opponent");

        //Add the response to every other player's update queue
        for(GameClient player : GameServer.getInstance().getActiveThreads().values())
            if(player != this.client)
                player.addResponseForUpdate(responsehit);
    }
}

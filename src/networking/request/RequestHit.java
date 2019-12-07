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
    private short hitPlayer;
    private short damage;
    private short numParticles;
    private float[] particlesPositions;

    public RequestHit() {
        responsehit = new ResponseHit();
    }

    @Override
    public void parse() throws IOException {
        //Get the player that was hit
        hitPlayer = DataReader.readShort(dataInput);
        //Get the amount of damage dealt
        damage = DataReader.readShort(dataInput);
        //Get the number of blood effects instantiated
        numParticles = DataReader.readShort(dataInput);

        //Make an array for the positions of the blood effects and populate it. There are three position floats and a three angle floats
        particlesPositions = new float[numParticles * 6];

        //Populate the array with the positions
        for(int i = 0; i < particlesPositions.length; i++)
            particlesPositions[i] = DataReader.readFloat(dataInput);
    }

    @Override
    public void doBusiness() throws Exception {
        //For debugging
        String character = null;

        switch (hitPlayer)
        {
            case 0:
                character = "Bog_lord";
                break;
            case 1:
                character = "Girl";
                break;
            case 2:
                character = "Max";
                break;
            case 3:
                character = "Winston";
                break;
        }

        System.out.println(this.client.getUser().getUserName() + " hit " + character);

        //Set the data for the response
        responsehit.setData(client.getUser().getCharacter(), hitPlayer, damage, particlesPositions);

        //Add the response to every other player's update queue
        for(GameClient player : GameServer.getInstance().getActiveThreads().values())
            if(player != this.client)
                player.addResponseForUpdate(responsehit);
    }
}

package networking.response.Lobby;

import metadata.Constants;
import networking.response.GameResponse;
import utility.GamePacket;

public class ResponseJoinGame extends GameResponse {

    //The character that the client will play
    String character;

    public ResponseJoinGame() {
        responseCode = Constants.SMSG_JOINGAME;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(Constants.characters.get(character).shortValue());
        return packet.getBytes();
    }
}

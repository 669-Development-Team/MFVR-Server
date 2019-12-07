package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponsePickup extends GameResponse {

    private String item;

    public ResponsePickup() {
        responseCode = Constants.SMSG_PICKUP;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addString(item);

        return packet.getBytes();
    }
}
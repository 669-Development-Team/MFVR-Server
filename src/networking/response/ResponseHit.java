package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseHit extends GameResponse {
    private short damage;

    public ResponseHit() {
        responseCode = Constants.SMSG_HIT;
    }

    public void setData(String attackingPlayer, short hitPlayer, short damage, float[] particlePositions) {
        this.damage = damage;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(damage);

        return packet.getBytes();
    }
}

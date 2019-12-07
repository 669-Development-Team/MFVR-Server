package networking.response;

import metadata.Constants;
import utility.GamePacket;

public class ResponseHit extends GameResponse {
    private String attackingPlayer;
    private short hitPlyaer;
    private short damage;
    private float[] particlePositions;

    public ResponseHit() {
        responseCode = Constants.SMSG_HIT;
    }

    public void setData(String attackingPlayer, short hitPlayer, short damage, float[] particlePositions) {
        this.attackingPlayer = attackingPlayer;
        this.hitPlyaer = hitPlayer;
        this.damage = damage;
        this.particlePositions = particlePositions;
    }

    @Override
    public byte[] constructResponseInBytes() {
        GamePacket packet = new GamePacket(responseCode);
        packet.addShort16(Constants.characters.get(attackingPlayer).shortValue());
        packet.addShort16(hitPlyaer);
        packet.addShort16(damage);
        packet.addShort16((short)(particlePositions.length/6));

        for(float position : particlePositions)
            packet.addFloat(position);

        return packet.getBytes();
    }
}

package collisiondetection;

import block.Block;
import sprites.Ball;
/**
 * interface to define the base work structure of HitListener.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the Block beingHit.
     * @param hitter the Ball that's doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}
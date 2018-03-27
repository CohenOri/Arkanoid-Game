package collisiondetection;

import block.Block;
import sprites.Ball;
/**
 * Listener to keeps track of Blocks hitting in order to count score.
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;
    /**
     * @param scoreCounter receives scoreCounter to update whenever hit occur.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * updates the counter accoriding to the hit event.
     * @param beingHit - beingHit block
     * @param hitter - hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // if hit & ruined block
        if (beingHit.getHitPoints() <= 0) {
            beingHit.removeHitListener(this);
            this.currentScore.increase(15);
            return;
        }
        // if only hit block
        if (true) {
            this.currentScore.increase(5);
        }
    }
}
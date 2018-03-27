package collisiondetection;

import block.Block;
import level.GameLevel;
import sprites.Ball;

/**
 * a BallRemover Listener is in charge of removing balls from the gameLevel, as
 * well as keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;
    /**
     * create BallRemover listener.
     * @param gameLevel - to remove the ball from.
     * @param remainingBalls Counter to update when ball is removed.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }
    /**
     * remove the ball when hit death block, remove the listener from the ball.
     * decrease the amount of balls in the game.
     * @param beingHit - beingHit block
     * @param hitter - hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGameLevel(this.gameLevel);
        hitter.removeHitListener(this);
        this.remainingBalls.decrease(1);
    }

}

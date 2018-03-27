package collisiondetection;

import block.Block;
import level.GameLevel;
import sprites.Ball;
/**
 * a BlockRemover Listener is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter remainingBlocks;
    /**
     * Create BlockRemover listener.
     * @param gameLevel - to remove the block from.
     * @param remainingBlocks Counter to update when block is removed.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }
    /**
     * Removes the block that has been hit if needed.
     * @param beingHit - beingHit block
     * @param hitter - hitter ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // if block should be removed, remove it and remove BlockRemover
        // Listener of him
        // update remainingBlocks counter accordingly
        if (beingHit.getHitPoints() <= 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGameLevel(this.gameLevel);
            this.remainingBlocks.decrease(1);
        }
    }
}

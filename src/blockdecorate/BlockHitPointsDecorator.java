package blockdecorate;

import block.Block;

/**
 * The Class BlockHitPointsDecorator.
 */
public class BlockHitPointsDecorator extends BlockCreatorDecorator {
    /** The hit points. */
    private int hitPoints;

    /**
     * Instantiates a new block hit points decorator.
     * Receives hitPoints and set it to given block.
     * @param decorated the decorated
     * @param hitPoints the hit points
     */
    public BlockHitPointsDecorator(BlockCreator decorated, String hitPoints) {
        super(decorated);
        this.hitPoints = Integer.parseInt(hitPoints);
    }

    /**
     * Create a block at the specified location.
     * Set hitPoints to block. in order to later draw it correctly.
     * @param x the x position
     * @param y the y position
     * @return the block
     */
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setHitPoints(this.hitPoints);
        return b;
    }
}

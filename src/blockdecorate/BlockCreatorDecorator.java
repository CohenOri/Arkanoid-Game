package blockdecorate;

import block.Block;

/**
 * Decorator class according to decorator Pattern.
 */
public abstract class BlockCreatorDecorator implements BlockCreator {
    /** The decorated block. */
    private BlockCreator decoratedBlock;

    /**
     * Instantiates a new block creator decorator.
     *
     * @param setDecoratedBlock set decorated block
     */
    public BlockCreatorDecorator(BlockCreator setDecoratedBlock) {
        this.decoratedBlock = setDecoratedBlock;
    }
    /**
     * Create a block at the specified location.
     * @param xpos the x position
     * @param ypos the y position
     * @return the block
     */
    public Block create(int xpos, int ypos) {
        return this.decoratedBlock.create(xpos, ypos);
    }
}

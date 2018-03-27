package blockdecorate;

import block.Block;

/**
 * The Class BlockWidthDecorator.
 */
public class BlockWidthDecorator extends BlockCreatorDecorator {
    /** The width. */
    private int width;

    /**
     * Instantiates a new block width decorator.
     * Receives width and set it to given block.
     * @param setDecoratedBlock the set decorated block
     * @param width the width
     */
    public BlockWidthDecorator(BlockCreator setDecoratedBlock, String width) {
        super(setDecoratedBlock);
        this.width = Integer.parseInt(width);
    }

    /**
     * Create a block at the specified location.
     * Set width to block. in order to later draw it correctly.
     * @param x the x position
     * @param y the y position
     * @return the block
     */
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setWidth(this.width);
        return b;
    }
}

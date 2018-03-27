package blockdecorate;

import block.Block;

/**
 * The Class BlockHeightDecorator.
 */
public class BlockHeightDecorator extends BlockCreatorDecorator {
    /** The height. */
    private int height;

    /**
     * Instantiates a new block height decorator.
     * Receives height and set it to given block.
     * @param setDecoratedBlock the set decorated block
     * @param height the height
     */
    public BlockHeightDecorator(BlockCreator setDecoratedBlock, String height) {
        super(setDecoratedBlock);
        this.height = Integer.parseInt(height);
    }

    /**
     * Create a block at the specified location.
     * Set height to block. in order to later draw it correctly.
     * @param x the x position
     * @param y the y position
     * @return the block
     */
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        b.setHeight(this.height);
        return b;
    }
}

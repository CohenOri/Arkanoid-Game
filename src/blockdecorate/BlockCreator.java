package blockdecorate;

import block.Block;

/**
 * The Interface BlockCreator.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos the x position
     * @param ypos the y position
     * @return the block
     */
    Block create(int xpos, int ypos);
}
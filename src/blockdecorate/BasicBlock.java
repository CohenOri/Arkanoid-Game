package blockdecorate;

import block.Block;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;

/**
 * The Class BasicBlock defines basic block.
 */
public class BasicBlock implements BlockCreator {

    /**
     * Instantiates a new basic block.
     */
    public BasicBlock() {

    }

    /**
     * Create a block at the specified location.
     * create BasicBlock. later we will update him.
     * @param xpos the x position
     * @param ypos the y position
     * @return the block
     */
    public Block create(int xpos, int ypos) {
        return new Block(new Rectangle(new Point(xpos, ypos), 50, 50));
    }

}

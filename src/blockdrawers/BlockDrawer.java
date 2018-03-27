package blockdrawers;

import biuoop.DrawSurface;
import geometryprimitives.Rectangle;

/**
 * The Interface BlockDrawer.
 */
public interface BlockDrawer {

    /**
     * Draw.
     * BlockCreator object holds a way to draw the block.
     * @param d the DrawSurface to draw on
     * @param r the Rectangle to draw
     */
    void draw(DrawSurface d, Rectangle r);

}

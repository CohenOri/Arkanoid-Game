package blockdrawers;

import biuoop.DrawSurface;
import geometryprimitives.Rectangle;

/**
 * The Class ColoredBlockDrawer.
 */
public class ColoredBlockDrawer implements BlockDrawer {
    /** The color. */
    private java.awt.Color color;

    /**
     * Instantiates a new colored block drawer.
     * draws the block fill using the given color.
     * @param setColor the fill Color.
     */
    public ColoredBlockDrawer(java.awt.Color setColor) {
        this.color = setColor;
    }
    /**
     * Draw the block's fill using the given color.
     * @param d the DrawSurface to draw on
     * @param r the Rectangle to draw
     */
    public void draw(DrawSurface d, Rectangle r) {
        d.setColor(this.color);
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), (int) r.getWidth(),
                (int) r.getHeight());
    }
}

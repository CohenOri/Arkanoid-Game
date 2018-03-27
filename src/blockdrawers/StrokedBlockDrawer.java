package blockdrawers;

import biuoop.DrawSurface;
import geometryprimitives.Rectangle;

/**
 * The Class StrokedBlockDrawer.
 */
public class StrokedBlockDrawer implements BlockDrawer {
    private java.awt.Color color;

    /**
     * Instantiates a new stroked block drawer.
     * draws the block stroke using the given color.
     * @param setColor the stroke Color.
     */
    public StrokedBlockDrawer(java.awt.Color setColor) {
        this.color = setColor;
    }
    /**
     * Draw the block's stroke using the given color.
     * @param d the DrawSurface to draw on
     * @param r the Rectangle to draw
     */
    public void draw(DrawSurface d, Rectangle r) {
        d.setColor(this.color);
        d.drawRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), (int) r.getWidth(),
                (int) r.getHeight());
    }

}

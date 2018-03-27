package blockdrawers;

import biuoop.DrawSurface;
import geometryprimitives.Rectangle;

/**
 * The Class ImagedBlockDrawer.
 */
public class ImagedBlockDrawer implements BlockDrawer {
    /** The image. */
    private java.awt.Image image;

    /**
     * Instantiates a new imaged block drawer.
     * draws the block using the given image.
     * @param setImage the image to draw with.
     */
    public ImagedBlockDrawer(java.awt.Image setImage) {
        this.image = setImage;
    }

    /**
     * Draw the block using the given image.
     * @param d the DrawSurface to draw on
     * @param r the Rectangle to draw
     */
    public void draw(DrawSurface d, Rectangle r) {
        d.drawImage((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), this.image);
    }
}

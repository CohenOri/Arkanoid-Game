package level;

import biuoop.DrawSurface;
import biuoop.GUI;
import sprites.Sprite;

/**
 * The Class GenericImageBackground.
 */
public class GenericImageBackground implements Sprite {
    private DrawSurface drawSurface;
    private java.awt.Image backgroundImage;

    /**
     * Instantiates a new generic image background.
     * @param g Draws the background on the given GUI
     * @param setBackgroundImage the set background image
     */
    public GenericImageBackground(GUI g, java.awt.Image setBackgroundImage) {
        this.drawSurface = g.getDrawSurface();
        this.backgroundImage = setBackgroundImage;
    }

    /**
     * Draw on.
     * @param d DrawSurface to draw on it the actual drawing.
     */
    public void drawOn(DrawSurface d) {
        d.drawImage(0, 30, this.backgroundImage);
    }

    /**
     * does nothing when timePasses. static Background.
     * @param dt the dt
     */
    public void timePassed(double dt) {
        // do nothing static background
    }

    /**
     * Adds the to game level.
     * @param g adds the background to the given GameLevel as a one of the
     * sprites.
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Gets the background draw surface.
     * @return return the DrawSurface the background drawn on
     */
    public DrawSurface getBackgroundDrawSurface() {
        return this.drawSurface;
    }
}

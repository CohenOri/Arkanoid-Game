package level;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.GUI;
import sprites.Sprite;

/**
 * The Class GenericColorBackground.
 */
public class GenericColorBackground implements Sprite {
    private DrawSurface drawSurface;
    private Color backgroundColor;

    /**
     * Instantiates a new generic color background.
     * @param g Draws the background on the given GUI
     * @param setBackgroundColor the set background color
     */
    public GenericColorBackground(GUI g, java.awt.Color setBackgroundColor) {
        this.drawSurface = g.getDrawSurface();
        this.backgroundColor = setBackgroundColor;
    }

    /**
     * Draw on.
     * @param d DrawSurface to draw on it the actual drawing.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.backgroundColor);
        d.fillRectangle(0, 30, 800, 570);
    }

    /**
     * does nothing when timePasses.
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

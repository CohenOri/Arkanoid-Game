package sprites;

import biuoop.DrawSurface;
import collisiondetection.Counter;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import level.GameLevel;
import utility.ColorsParser;

/**
 * in in charge of display the scores at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreToDisplay;
    /**
     * @param score to Display.
     */
    public ScoreIndicator(Counter score) {
        this.scoreToDisplay = score;
    }
    /**
     * @param surface to draw Lives on.
     */
    public void drawOn(DrawSurface surface) {
        // set the color we will use to draw as Block member color
        ColorsParser cParser = new ColorsParser();
        surface.setColor(cParser.colorFromString("RGB(66,220,244)"));
        // get the data needed to draw rectangle
        Rectangle rect = new Rectangle(new Point(0, 0), 800, 30);
        int x = (int) rect.getUpperLeft().getX();
        int y = (int) rect.getUpperLeft().getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        surface.fillRectangle(x, y, width, height);
        surface.setColor(java.awt.Color.BLACK);
        surface.drawRectangle(x, y, width, height);
        surface.setColor(java.awt.Color.BLACK);
        // convert scoreToDisplay counter to String in order to draw it on
        // surface
        String score = Integer.toString(this.scoreToDisplay.getValue());
        surface.drawText(x + 15, y + 25, "Score: " + score, 20);
    }
    /**
     * does nothing when time passes.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }
    /**
     * Adds ScoreIndicator object to GameLevel object. ScoreIndicator is Sprite object.
     * @param g - GameLevel object to add ScoreIndicator to
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }
}

package sprites;

import biuoop.DrawSurface;
import collisiondetection.Counter;
import level.GameLevel;

/**
 * in charge of displaying the current number of lives on the screen.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    /**
     * @param numberOflives to Display.
     */
    public LivesIndicator(Counter numberOflives) {
        this.lives = numberOflives;
    }
    /**
     * @param surface to draw Lives on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(java.awt.Color.BLACK);
        // convert lives counter to String in order to draw it on surface
        String livesAsString = Integer.toString(this.lives.getValue());
        surface.drawText(surface.getWidth() / 2 - 60, 25, "Lives: " + livesAsString, 20);
    }
    /**
     * does nothing when time passes.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }
    /**
     * Adds LivesIndicator object to GameLevel object. LivesIndicator is Sprite object.
     * @param g - GameLevel object to add LivesIndicator to
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }
}

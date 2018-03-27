package sprites;
import biuoop.DrawSurface;
import level.GameLevel;

/**
 * Each object on the screen is Sprite,
 * therefore we define Sprite interface. each object shown on screen
 * has to imlement it.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d - DrawSurface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * method to notify the sprite that time has passed.
     * @param dt - difference in time
     */
    void timePassed(double dt);
    /**
     * method to define how to add the sprite to the game.
     * @param g - GameLevel object to add sprite to
     */
    void addToGameLevel(GameLevel g);
}
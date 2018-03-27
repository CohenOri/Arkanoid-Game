package sprites;

import biuoop.DrawSurface;
import level.GameLevel;

/**
 * in charge of displaying the level name  on the screen.
 */
public class LevelIndicator implements Sprite {
    private String levelName;

    /**
     * @param levelName String lvl name.
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }
    /**
     * @param surface to draw lvl name on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(java.awt.Color.BLACK);
        surface.drawText(surface.getWidth() - 330, 25, "Level Name: " + this.levelName, 20);
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

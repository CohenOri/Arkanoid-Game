package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * SpriteCollection object inculdes all the Sprites objects on the screen
 * each object on the screen is a Sprite.
 * they are stroed in a List of "spritesList".
 */
public class SpriteCollection {
    private List<Sprite> spritesList = new ArrayList<Sprite>();

    /**
     * Create SpriteCollection with no Sprites add Sprites later with addSprites
     * method.
     */
    public SpriteCollection() {
    }

    /**
     * Add Sprite object to spritesList.
     * @param s - Sprite object
     */
    public void addSprite(Sprite s) {
        spritesList.add(s);

    }

    /**
     * calls timePassed() on all sprites.
     * @param dt the dt to move according to.
     */
    public void notifyAllTimePassed(double dt) {
        // copy of SpiteList to run on, since original may be changed while it is being iterated
        // which will cause an exception, therefore perform the iteration over a copy of the list instead.
        List<Sprite> copySpritesList = new ArrayList<Sprite>(this.spritesList);
        for (Sprite s : copySpritesList) {
            s.timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d - DrawSurface object to drawOn
     */
    public void drawAllOn(DrawSurface d) {
        // copy of SpiteList to run on, since original may be changed while it is being iterated
        // which will cause an exception, therefore perform the iteration over a copy of the list instead.
        List<Sprite> copySpritesList = new ArrayList<Sprite>(this.spritesList);
        for (Sprite s : copySpritesList) {
            s.drawOn(d);
        }
    }
    /**
     * removes the given Sprite object from the SpriteCollection.
     * @param s Sprite object to remove
     */
    public void removeSprite(Sprite s) {
        this.spritesList.remove(s);
    }

}
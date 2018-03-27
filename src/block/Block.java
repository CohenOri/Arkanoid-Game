package block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biuoop.DrawSurface;
import blockdrawers.BlockDrawer;
import blockdrawers.ColoredBlockDrawer;
import collisiondetection.HitListener;
import collisiondetection.HitNotifier;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import geometryprimitives.Velocity;
import level.GameLevel;
import sprites.Ball;
import sprites.Collidable;
import sprites.Sprite;

/**
 * Block Class to define block and bricks. each block made of Rectangle which
 * define it, hitPoints counter and has a color
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rect;
    private int hitPoints;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private BlockDrawer defaultStrokeDrawer;
    private BlockDrawer defaultColoredDrawer; // if there is no k:drawer, it is
                                              // just fill:color...
    // hold drawers for each hitPoint,
    // exp. if block has 1 hitPoint draw him this way, if it has 2 draw it other
    // way.
    private Map<Integer, BlockDrawer> drawerByHitPoints;

    /**
     * Constructor to create new block with default color (red), using Rectangle
     * and hitPoint counter (to count how much hits the object can take).
     * @param r Rectangle object
     * @param hitPointsNumber counter to count how much hits the object can take
     */
    public Block(Rectangle r, int hitPointsNumber) {
        this.rect = r;
        this.hitPoints = hitPointsNumber;
        // this.defaultColoredDrawer = new
        // ColoredBlockDrawer(defaultInnerColor);
        // this.defaultStrokeDrawer = new
        // StrokedBlockDrawer(defaultStrokeColor);
        this.drawerByHitPoints = new HashMap<>();
    }

    /**
     * Instantiates a new block.
     * @param r the rect define block.
     */
    public Block(Rectangle r) {
        this.rect = r;
        this.hitPoints = 1;
        this.defaultColoredDrawer = new ColoredBlockDrawer(java.awt.Color.RED);
        this.drawerByHitPoints = new HashMap<>();
    }

    /**
     * Sets the default stroked block drawer.
     * @param d the new default stroked block drawer
     */
    public void setDefaultStrokedBlockDrawer(BlockDrawer d) {
        this.defaultStrokeDrawer = d;
    }

    /**
     * Sets the default colored block drawer.
     * @param drawer the new default colored block drawer
     */
    public void setDefaultColoredBlockDrawer(BlockDrawer drawer) {
        this.defaultColoredDrawer = drawer;
    }

    /**
     * Gets the hit points.
     * @return object hitPoints counter.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Sets object hitPoints counter.
     * @param hitPointsNumber int hitPoints counter
     */
    public void setHitPoints(int hitPointsNumber) {
        this.hitPoints = hitPointsNumber;
    }

    /**
     * Gets the collision rectangle.
     * @return the Rectangle object the block made of
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Receives collisionPoint and currentVelocity before hitting the block
     * calculates and return object's Velocity after the hit.
     * @param hitter hitter ball
     * @param collisionPoint Point object
     * @param currentVelocity Velocity object
     * @return Velocity object after it hits the block
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // the newVelocity after collision, as default set to be currentVelocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        if (collisionPoint.getX() == this.rect.getUpperLeft().getX()) {
            // if hit the left corner
            newVelocity.setDx(-1 * currentVelocity.getDx());
        }
        if (collisionPoint.getY() == this.rect.getUpperLeft().getY()) {
            // if hit the top corner
            newVelocity.setDy(-1 * currentVelocity.getDy());
        }
        if (collisionPoint.getX() == this.rect.getUpperLeft().getX() + this.rect.getWidth()) {
            // if hit the right corner
            newVelocity.setDx(-1 * currentVelocity.getDx());
        }
        if (collisionPoint.getY() == this.rect.getUpperLeft().getY() + this.rect.getHeight()) {
            // if hit the bottom corner
            newVelocity.setDy(-1 * currentVelocity.getDy());
        }
        // decrease hitPoints by one, since the object has been hit
        this.hitPoints--;
        // notify the hitter object (= the ball) it hit something
        this.notifyHit(hitter);
        return newVelocity;
    }

    /**
     * draw the block as rectangle on the given DrawSurface.
     * @param surface drawSurface object to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        if (this.hitPoints <= 0) {
            // set the color we will use to draw as Block member color
            surface.setColor(Color.red);
            // get the data needed to draw rectangle
            int x = (int) this.rect.getUpperLeft().getX();
            int y = (int) this.rect.getUpperLeft().getY();
            int width = (int) this.rect.getWidth();
            int height = (int) this.rect.getHeight();
            surface.fillRectangle(x, y, width, height);
            surface.setColor(java.awt.Color.BLACK);
            surface.drawRectangle(x, y, width, height);
            surface.setColor(java.awt.Color.WHITE);
        } else {
            // if drawerByHitPoints hold drawer for the given hit points use it,
            // otherwise just draw the default drawer
            BlockDrawer fill = getFillDrawer(this.hitPoints);
            BlockDrawer stroke = getStrokeDrawer();
            fill.draw(surface, this.rect);
            // if block has stroke draw it, otherwise don't
            if (stroke != null) {
                stroke.draw(surface, rect);
            }
        }
    }

    /**
     * does noting.
     * @param dt the dt
     */
    public void timePassed(double dt) {

    }

    /**
     * Adds Block object to GameLevel object. Block is Sprite & Collidable
     * object.
     * @param g GameLevel object to add block to
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * removes the block from the given GameLevel.
     * @param gameLevel GameLevel object to remove block from
     */
    public void removeFromGameLevel(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Notify all listeners about a hit event of the Block and the hitter Ball.
     * @param hitter hitter ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Adds the hit listener.
     * @param hl adds the given hitListener to the Block hitListeners List.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Removes the hit listener.
     * @param hl remove the given hitListener from the Block hitListeners List.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Gets the rect.
     * @return the rect.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Sets the rect.
     * @param rectToSet the rect to set.
     */
    public void setRect(Rectangle rectToSet) {
        this.rect = rectToSet;
    }

    /**
     * Update the current rect to new width & height.
     * @param width set width for rect
     * @param height set height for rect
     */
    public void setRect(int width, int height) {
        Rectangle rectangle = new Rectangle(this.rect.getUpperLeft(), width, height);
        this.rect = rectangle;
    }

    /**
     * Update the current rect to new x, y, width & height.
     * @param x new x
     * @param y new y
     * @param width new width
     * @param height new height
     */
    public void setRect(int x, int y, int width, int height) {
        Rectangle rectangle = new Rectangle(new Point(x, y), width, height);
        this.rect = rectangle;
    }

    /**
     * Update the current rect to new width.
     * @param width new width
     */
    public void setWidth(int width) {
        Rectangle rectangle = new Rectangle(this.rect.getUpperLeft(), width, this.rect.getHeight());
        this.rect = rectangle;
    }

    /**
     * Update the current rect to new height.
     * @param height new height
     */
    public void setHeight(int height) {
        Rectangle rectangle = new Rectangle(this.rect.getUpperLeft(), this.rect.getWidth(), height);
        this.rect = rectangle;
    }

    /**
     * Adds drawer to Block. add drawer by hitPoints as key, hitPoints:drawer
     * this way we know how to draw using given hitPoints.
     * @param bDrawer Drawer
     * @param currentHitPoints hitPoints matching drawer
     */
    public void addFillDrawer(BlockDrawer bDrawer, int currentHitPoints) {
        // add drawer by hitPoints as key, hitPoints:drawer
        // this way we know how to draw using given hitPoints.
        this.drawerByHitPoints.put(currentHitPoints, bDrawer);
    }

    /**
     * Gets the fill drawer.
     * @param currentHitPoints in order to return the matching drawer.
     * @return getFillDrawer matching the given hitPoints
     */
    public BlockDrawer getFillDrawer(int currentHitPoints) {
        if (this.drawerByHitPoints.containsKey(currentHitPoints)) {
            return this.drawerByHitPoints.get(currentHitPoints);
        }
        return defaultColoredDrawer;
    }

    /**
     * Adds as stroke drawer to Block.
     * @param sDrawer stroke Drawer to set.
     */
    public void addStrokeDrawer(BlockDrawer sDrawer) {
        this.defaultStrokeDrawer = sDrawer;
    }

    /**
     * Gets the stroke drawer.
     * @return stroke Drawer of the block.
     */
    public BlockDrawer getStrokeDrawer() {
        if (this.defaultStrokeDrawer != null) {
            return this.defaultStrokeDrawer;
        }
        return null;
    }

}

package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import block.Block;
import collisiondetection.GameEnvironment;
import collisiondetection.HitListener;
import geometryprimitives.Line;
import geometryprimitives.Point;
import geometryprimitives.Velocity;
import level.GameLevel;

/**
 * Class Ball, to define a ball (actually a circle) on the screen. Ball is a
 * Sprite, therefore it imlements Sprite interface
 */
public class Ball implements Sprite, HitListener {

    private Point centerPoint; // center point of the ball
    private int radius;
    private java.awt.Color ballColor;
    private Velocity v; // speed, using dx and dy as direction
    // (0,0) is the topLeft, and (1920,1080) bottom right (screen depandent)
    private GameEnvironment gameEnvironment; // gameEnviroment, all the Colidables objects around ball
    private List<HitListener> hitListeners = new ArrayList<HitListener>();


    /**
     * constructor using a Point as center, r as radius, color as color, v as
     * Velocity and gEnvironment as GameEnvironment. Creates a Ball aware of its
     * enviroment with the given detalis.
     * @param center - ball center Point
     * @param r - radius
     * @param color - ball color
     * @param v - ball Velocity
     * @param gEnvironment - GameEnvironment of ball (stores all the collidable objects)
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity v, GameEnvironment gEnvironment) {
        this.centerPoint = center;
        this.radius = r;
        this.ballColor = color;
        this.v = v; // default Velocity value
        this.gameEnvironment = gEnvironment;
    }

    /**
     * constructor using a (x,y) coordinates as a center, r as radius, color as
     * color, v as Velocity and gEnvironment as GameEnvironment. Creates a Ball
     * aware of its enviroment with the given detalis
     * @param x - coordinate
     * @param y - coordinate
     * @param r - radius
     * @param color - ball color
     * @param v - ball Velocity
     * @param gEnvironment - GameEnvironment of ball (stores all the collidable objects)
     */
    public Ball(int x, int y, int r, java.awt.Color color, Velocity v, GameEnvironment gEnvironment) {
        this.centerPoint = new Point(x, y);
        this.radius = r;
        this.ballColor = color;
        this.v = v;
        this.gameEnvironment = gEnvironment;
    }

    /**
     * @return Center Point x coordinate.
     */
    public int getX() {
        return (int) this.centerPoint.getX();
    }

    /**
     * @return Center Point y coordinate.
     */
    public int getY() {
        return (int) this.centerPoint.getY();
    }

    /**
     * @return ball size (radius)
     */
    public int getSize() {
        return (int) this.radius;
    }

    /**
     * @return ball color (java.awt.Color)
     */
    public java.awt.Color getColor() {
        return ballColor;
    }

    /**
     * draw the ball using its color and center Point on the given DrawSurface.
     * @param surface - drawSurface object to draw on it.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.ballColor);
        surface.fillCircle((int) this.centerPoint.getX(), (int) this.centerPoint.getY(), this.radius);
    }

    /**
     * set the ball velocity using dx, dy doubles.
     * @param dx - double dx direction
     * @param dy - double dy direction
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * @return velocity of the ball (as Velocity object)
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * set the ball velocity using Velocity object.
     * @param velocity - velocity Object
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
    }

    /**
     * moves the ball one step "moveOneStep" based on its velocity, in case it
     * hits any Collidable object changes the velocity accoridingly if it
     * doesn't hit anything it updates the ball location exp. ball was on
     * (10,10) and its velocity is (5,2) then its new center is (15,2).
     * @param dt the dt to move the ball according to.
     */
    public void timePassed(double dt) {
        // temp potential line end Point for calculations, "if ball doesn't hit
        // anything"
        Point tempPotentialLineEndPoint = this.v.applyToPoint(new Point(this.centerPoint.getX(),
                this.centerPoint.getY()), dt);
        Line trajectory = new Line(this.centerPoint, tempPotentialLineEndPoint);
        Velocity newVelocity = this.v; //default value
        // if its going to hit anything
        if (this.gameEnvironment.getClosestCollision(trajectory) != null) {
            // find the closet collisionPoint and collisionObject (the object
            // ball is going to hit)
            Point collisionPoint = this.gameEnvironment.getClosestCollision(trajectory).collisionPoint();
            Collidable obj = this.gameEnvironment.getClosestCollision(trajectory).collisionObject();
            // if ball center is inside a collidable object
            if (obj.getCollisionRectangle().insideRectangle(this.centerPoint) && obj instanceof Paddle) {
                // the ball will be moved to the top of the paddle
                this.centerPoint = new Point(this.centerPoint.getX(),
                        this.centerPoint.getY() - obj.getCollisionRectangle().getHeight());
                newVelocity = obj.hit(this, collisionPoint, this.v);
            } else {
                // move the ball to "almost" the hit point, but just slightly before
                // it.
                this.centerPoint.setX(collisionPoint.getX() - 0.01 * this.v.getDx());
                this.centerPoint.setY(collisionPoint.getY() - 0.01 * this.v.getDy());
                // notify the hit object (using its hit() method) that a collision
                // occurred.
                newVelocity = obj.hit(this, collisionPoint, this.v);
            }
            // update the velocity (of ball) to the new velocity returned by the
            // hit() method.
            this.v = newVelocity;
        } else {
            // if its won't hit anything
            this.centerPoint = this.getVelocity().applyToPoint(this.centerPoint, dt);
        }
    }

    /**
     * Adds ball object to GameLevel object, Ball is only Sprite object.
     * @param g - GameLevel object to add ball to
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // empty for now
    }
    /**
     * removes ball from the given game.
     * @param g GameLevel object to remove ball from
     */
    public void removeFromGameLevel(GameLevel g) {
        g.removeSprite(this);
    }
    /**
     * does nothing for now.
     * @param beingHit can be used to notify block ball hit him, for now now use.
     */
    private void notifyHit(Block beingHit) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
           hl.hitEvent(beingHit, this);
        }
     }
    /**
     * @param hl - adds the given hitListener to the Ball hitListeners List.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * @param hl - remove the given hitListener from the Ball hitListeners List.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
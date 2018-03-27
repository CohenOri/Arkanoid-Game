package sprites;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import geometryprimitives.Velocity;
import level.GameLevel;

/**
 * A class to define Paddle which is a Sprite and a Collidable object
 * Controlled by the keyboard and defined by Rectangle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private DrawSurface dSurface;
    private int speed;
    /**
     * constructor using KeyboardSensor and Rectangle objects. [CAREFUL - no dSurface] creates a paddle
     * controlled by keyboard with default size and location.
     * @param k - KeyboardSensor object
     */
    public Paddle(biuoop.KeyboardSensor k) {
        this.keyboard = k;
        this.rect = new Rectangle(new Point(400, 400), 200, 100);
    }

    /**
     * constructor using KeyboardSensor and Rectangle objects. [CAREFUL - no dSurface] creates a paddle
     * controlled by keyboard with the given Rectangle as size and location.
     * @param k - KeyboardSensor object
     * @param r - Rectangle object.
     */
    public Paddle(biuoop.KeyboardSensor k, Rectangle r) {
        this.keyboard = k;
        this.rect = r;
    }
    /**
     * constructor using KeyboardSensor, Rectangle and DrawSurface objects. creates a paddle
     * controlled by keyboard with the given Rectangle as size and location. it will move within
     * the given DrawSurface only
     * @param k - KeyboardSensor object
     * @param r - Rectangle object.
     * @param d - drawSurface object the paddle will be drawn on
     * @param movingSpeed - moving speed of the paddle
     */
    public Paddle(biuoop.KeyboardSensor k, Rectangle r, DrawSurface d, int movingSpeed) {
        this.keyboard = k;
        this.rect = r;
        this.dSurface = d;
        this.speed = movingSpeed;
    }

    /**
     * Moves the pad to the left. if possible.
     * @param dt the dt to move accoriding to
     */
    public void moveLeft(double dt) {
        double rectUpperLeftX = rect.getUpperLeft().getX();
        double rectUpperLeftY = rect.getUpperLeft().getY();
        Point rectUpdatedUpperleft = new Point(rectUpperLeftX - (dt * this.speed), rectUpperLeftY);
        // only if the paddle "UpdatedUpperleft" within the specified drawSurface move it
        if (rectUpdatedUpperleft.getX() >= 0) {
            this.rect.setUpperLeft(rectUpdatedUpperleft);
        }
    }

    /**
     * Moves the pad to the Right. if possible.
     * @param dt the dt to move accoriding to
     */
    public void moveRight(double dt) {
        double rectUpperLeftX = rect.getUpperLeft().getX();
        double rectUpperLeftY = rect.getUpperLeft().getY();
        Point rectUpdatedUpperleft = new Point(rectUpperLeftX + (dt * this.speed), rectUpperLeftY);
        // only if the paddle "UpdatedUpperleft" within the specified drawSurface move it
        if (rectUpdatedUpperleft.getX() <= dSurface.getWidth() - this.rect.getWidth()) {
        this.rect.setUpperLeft(rectUpdatedUpperleft);
        }
    }

    /*
     * if I want to make it more fun in the future... /** Moves the pad up.
     * public void moveUp() {
     * } /** Moves the pad down.
     * public void moveDown() {
     * }
     */

    /**
     * Checks what key has been pressed and moves the pad accordingly.
     * @param dt the dt to move accoriding to.
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        /*
         * if I want to make it more fun in the future... if
         * (keyboard.isPressed(KeyboardSensor.UP_KEY)) { this.moveUp(); } if
         * (keyboard.isPressed(KeyboardSensor.DOWN_KEY)) { this.moveDown(); }
         */
    }

    /**
     * draw the Paddle as rectangle on the given DrawSurface.
     * @param d - drawSurface object to draw on it.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(java.awt.Color.YELLOW);
        // get the data needed to draw rectangle
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        d.fillRectangle(x, y, width, height);
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * @return Rectangle object which define the paddle
     */
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    /** Receives collisionPoint and currentVelocity before hitting the paddle
     * calculates and return object's Velocity after the hit.
     * @param hitter - hitter Ball
     * @param collisionPoint - Point object
     * @param currentVelocity - Velocity object
     * @return Velocity object after it hits the paddle
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // the newVelocity after collision, as default set to be currentVelocity
        Velocity newVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        double paddleSize = this.rect.getWidth();
        double fifthOfPaddleSize = paddleSize / 5;
        double velocityVectSize = currentVelocity.getVelocitySize();
        if (this.rect.getUpperLeft().getX() <= collisionPoint.getX()
                && collisionPoint.getX() < this.rect.getUpperLeft().getX() + 1 * fifthOfPaddleSize) {
            // if hit the left-most region
            newVelocity = newVelocity.fromAngleAndSpeed(-60, velocityVectSize);
        }
        if (this.rect.getUpperLeft().getX() + 1 * fifthOfPaddleSize <= collisionPoint.getX()
                && collisionPoint.getX() < this.rect.getUpperLeft().getX() + 2 * fifthOfPaddleSize) {
            // if hit the second from left region
            newVelocity = newVelocity.fromAngleAndSpeed(-30, velocityVectSize);
        }
        if (this.rect.getUpperLeft().getX() + 2 * fifthOfPaddleSize <= collisionPoint.getX()
                && collisionPoint.getX() < this.rect.getUpperLeft().getX() + 3 * fifthOfPaddleSize) {
            // if hit the third from left region - the middle DO NOTHING
            newVelocity = newVelocity; // checkStyle.. can be removed
        }
        if (this.rect.getUpperLeft().getX() + 3 * fifthOfPaddleSize <= collisionPoint.getX()
                && collisionPoint.getX() < this.rect.getUpperLeft().getX() + 4 * fifthOfPaddleSize) {
            // if hit the 4th from left region
            newVelocity = newVelocity.fromAngleAndSpeed(30, velocityVectSize);
        }
        if (this.rect.getUpperLeft().getX() + 4 * fifthOfPaddleSize <= collisionPoint.getX()
                && collisionPoint.getX() <= this.rect.getUpperLeft().getX() + 5 * fifthOfPaddleSize) {
            // if hit the 5th from left region
            newVelocity = newVelocity.fromAngleAndSpeed(60, velocityVectSize);
        }
        // change the dy value of velicity to the opposite direction
        newVelocity.setDy(-1 * newVelocity.getDy());
        return newVelocity;
    }

    /**
     * Adds Paddle object to GameLevel object. Paddle is Sprite & Collidable object
     * @param g - GameLevel object to add block to
     */
    public void addToGameLevel(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * removes the paddle from the given GameLevel.
     * @param gameLevel GameLevel object to remove paddle from
     */
    public void removeFromGameLevel(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

}
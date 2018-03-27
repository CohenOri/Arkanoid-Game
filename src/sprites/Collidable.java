package sprites;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import geometryprimitives.Velocity;

/**
 * interface to define Collidable objects, object that ball can collide with.
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     * @return - the Rectangle object we collided with
     */
    Rectangle getCollisionRectangle();

    /**
     * hit method to notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param hitter - the hitter ball, the one who hit the Collidable object
     * @param collisionPoint - the point we colided with the object at
     * @param currentVelocity - the velocity we had when we colided with the object
     * @return our new Velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
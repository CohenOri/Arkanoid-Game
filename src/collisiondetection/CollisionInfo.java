package collisiondetection;
import geometryprimitives.Point;
import sprites.Collidable;

/**
 * Class to define CollisionInfo object
 * that holds collisionPoint & collisionObject (=the object we collided with).
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Constructor to build CollisionInfo object using the collisionPoint
     * and collisionObject (=the object we collided with).
     * @param collisionPoint - Point where the collision will occur
     * @param collisionObject - object we are going to collid with
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     * (= object we are going to collide with)
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
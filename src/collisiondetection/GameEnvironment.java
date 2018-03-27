package collisiondetection;
import java.util.ArrayList;
import java.util.List;

import geometryprimitives.Line;
import geometryprimitives.Point;
import sprites.Collidable;

/**
 * GameEnvironment object inculdes all the Collidable objects on the screen
 * they are stroed in a List of "obstacles" which cause the ball to change its
 * direction when it hits them.
 */
public class GameEnvironment {

    private List<Collidable> obstacles = new ArrayList<Collidable>();

    /**
     * Create GameEnvironment object with no obstacles (= Collidable objects) on the screen
     * you may add Collidables later with addCollidable method.
     */
    public GameEnvironment() {
    }

    /**
     * Adds the given collidable object to the environment.
     * @param c - collidable object to add
     */
    public void addCollidable(Collidable c) {
        obstacles.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end(). If this object
     * will not collide with any of the collidables in this collection (=obstacle list),
     * return null. Else, return the information about the closest collision that is
     * going to occur.
     * @param trajectory - a Line object with start and end Points
     * @return CollisionInfo object - which holds collisionPoint & Collision object - the object the ball hits.
     * or null if not going to hit anything
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // copy of obstacles list to run on, since original may be changed while it is being iterated
        // which will cause an exception, therefore perform the iteration over a copy of the list instead.
        List<Collidable> copyObstacles = new ArrayList<Collidable>(this.obstacles);
        // find the point with the minDistance from start of the line.
        // define minDistance to be the biggest number in the world and each
        // number will be smaller than him
        Double minDistance = Double.POSITIVE_INFINITY;
        CollisionInfo cInfo = null; // default - no collision
        /* check for each object in the collidable collection if Line is going to Collide with it
        if it is, find the closet one - the first object that ball is going to collide with since
        it collide only with it */
        for (Collidable c : copyObstacles) {
            // if trajectory has Collision with 'c' obstacle return collision
            // point else return null
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            // calculate the distance between trajectory.startPoint to collision
            // point (if exist)
            Double distance = null; // by default distance is null
            if (collisionPoint != null) {
                distance = trajectory.start().distance(collisionPoint);
                if (distance < minDistance) {
                    minDistance = distance;
                    // create collision info - that stores the minDistance
                    // collisionPoint & object
                    cInfo = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        return cInfo;
    }
    /**
     * @return List of Collidable objects.
     */
    public List<Collidable> getCollidables() {
        return this.obstacles;
    }
    /**
     * removes the given Collidable object from the GameEnviroment.
     * @param c Collidable object to remove
     */
    public void removeCollidables(Collidable c) {
        this.obstacles.remove(c);
    }

}
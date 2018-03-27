package geometryprimitives;
/**
 * Velocity specifies the change in position on the `x` and the `y` axis.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Create velocity object using dx and dy.
     * @param dx - change in the x axis
     * @param dy - change in the y axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * apply Velocity to given point. Take a point with position (x,y) and
     * returns it with position (x+dx, y+dy)
     * @param p - Point
     * @param dt - difference in time
     * @return update 'p' Point and returns it
     */
    public Point applyToPoint(Point p, double dt) {
        p.setX(p.getX() + dt * this.dx);
        p.setY(p.getY() + dt * this.dy);
        return p;
    }

    /**
     * @return the dx value of velocity vector.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the dy value of velocity vector.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Sets a new dx value to velocity vector.
     * @param newDx - newDx value
     */
    public void setDx(double newDx) {
        this.dx = newDx;
    }

    /**
     * Sets a new dy value to velocity vector.
     * @param newDy - newDy value
     */
    public void setDy(double newDy) {
        this.dy = newDy;
    }

    /**
     * Create Velocity vector using angle and speed, (actually a constrouctor).
     * recevies angle and speed and creates velocity based on it. exmp. instead
     * of dx=2, dy=0, you could specify (90, 2) meaning 2 units in the 90
     * degrees direction (assuming up is angle 0).
     * @param angle - degrees
     * @param speed - double size
     * @return - creates a Velocity vector, as constrouctor.
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        double dX = Math.sin(Math.toRadians(angle)) * speed;
        double dY = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dX, dY);
    }

    /**
     * return velocity vector size (often called speed).
     * @return velocity vector size as double
     */
    public double getVelocitySize() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }
}

package geometryprimitives;
/**
 * Class Point define Point object using (x,y) coordinates.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Creates a Point.
     * @param x - value
     * @param y - value
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * return the distance between this Point to the other Point.
     * @param other - Point
     * @return distance as double
     */
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * return true if Points are equal, false otherwise.
     * @param other - Point
     * @return true if this Point equal to other Point
     */
    public boolean equals(Point other) {
        if (this.x == other.getX() && this.y == other.getY()) {
            return true;
        }
        return false;
    }

    /**
     * @return x value of this Point.
     */
    public double getX() {
        return this.x;

    }

    /**
     * @return y value of this Point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets new x value to this point.
     * @param newX - value
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * Sets new y value to this point.
     * @param newY - value
     */
    public void setY(double newY) {
        this.y = newY;
    }

}

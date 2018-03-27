package geometryprimitives;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Rectangle to define a Rectangle object on the screen
 * using upperLeft Point, width and height.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Creates new rectangle with given Point and width/height.
     * @param upperLeft Point
     * @param width double
     * @param height double
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns a (possibly empty) List of intersection points with the specified line.
     * @param line - Line object to find intersection points of this Rectangle with
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List intersectionPoints(Line line) {
        // create four lines which define the Rectangle
        Line topLeftToBottomLeft = new Line(this.upperLeft,
                new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height));
        Line topLeftToTopRight = new Line(this.upperLeft,
                new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY()));
        Line topRightToBottomRight = new Line(new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY()),
                new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height));
        Line bottomLeftToBottomRight = new Line(new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height),
                new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height));
        // check if the lines which define the Rectangle intersect with the given line
        Point p; // temp Point to store intersection point if exist
        // create a list to store the intersectionPoints
        List<Point> intersectionPoints = new ArrayList<Point>();
        // check if given line has intersection Point with XXX, if it has store it in
        // the List "intersectionPoints"
        p = line.intersectionWith(topLeftToBottomLeft);
        if (p != null) {
            intersectionPoints.add(p);
        }
        p = line.intersectionWith(topLeftToTopRight);
        if (p != null) {
            intersectionPoints.add(p);
        }
        p = line.intersectionWith(topRightToBottomRight);
        if (p != null) {
            intersectionPoints.add(p);
        }
        p = line.intersectionWith(bottomLeftToBottomRight);
        if (p != null) {
            intersectionPoints.add(p);
        }
        return intersectionPoints;
    }

    /**
     * @return width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Set the width of the rectangle.
     * @param newWidth double
     */
    public void setWidth(double newWidth) {
        this.width = newWidth;
    }

    /**
     * Set the height of the rectangle.
     * @param newHeight double
     */
    public void setHeight(double newHeight) {
        this.height = newHeight;
    }

    /**
     * Set the UpperLeft Point of the rectangle.
     * @param newUpperLeftPoint Point object
     */
    public void setUpperLeft(Point newUpperLeftPoint) {
        this.upperLeft = newUpperLeftPoint;
    }

    /**
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Receives a Point and return true if it inside the rectangle or false otherwise.
     * @param pointToCheck - Point
     * @return true if it inside the rectangle or false otherwise
     */
    public Boolean insideRectangle(Point pointToCheck) {
        if (this.upperLeft.getX() <= pointToCheck.getX()) {
            if (this.upperLeft.getX() + this.width >= pointToCheck.getX()) {
                if (this.upperLeft.getY() <= pointToCheck.getY()) {
                    if (this.upperLeft.getY() + this.height >= pointToCheck.getY()) {
                        return true;
                    }
                }
            }
        }
        return false; //default
    }
}
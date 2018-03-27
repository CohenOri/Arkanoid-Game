package geometryprimitives;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Line class to define Line.
 */
public class Line {
    private Point pStart;
    private Point pEnd;

    /**
     * Creates a Line from two Point objects.
     * @param start - Point object
     * @param end - Point object
     */
    public Line(Point start, Point end) {
        this.pStart = start;
        this.pEnd = end;
    }

    /**
     * Creates a Line from two (x1,y1) & (x2,y2) cordinates - converts them to
     * Points objects.
     * @param x1 - cordinate
     * @param y1 - cordinate
     * @param x2 - cordinate
     * @param y2 - cordinate
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.pStart = new Point(x1, y1);
        this.pEnd = new Point(x2, y2);
    }

    /**
     * @return the length of the line (double).
     */
    public double length() {
        return this.pStart.distance(pEnd);
    }

    /**
     * @return the middle point of the line (Point).
     */
    public Point middle() {
        double midX = ((this.pStart.getX() + this.pEnd.getX()) / 2);
        double midY = ((this.pStart.getY() + this.pEnd.getY()) / 2);
        Point mid = new Point(midX, midY);
        return mid;
    }

    /**
     * @return the start point of the line (Point).
     */
    public Point start() {
        return this.pStart;
    }

    /**
     * @return the end point of the line.
     */
    public Point end() {
        return this.pEnd;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other - other Line object to check if this Line intresct with
     * @return True/False
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns the intersection point if the lines intersect, Returns null
     * otherwise.
     * @param other - other Line object to find intresction Point
     * @return p - intresction point or null.
     */
    public Point intersectionWith(Line other) {
        // find line kind "r=regular" or "v=vertical"
        char thisLineKind = lineKind(this.pStart, this.pEnd);
        char otherLineKind = lineKind(other.pStart, other.pEnd);
        // find each line max and min Y and X cordintaes
        double thisMaxY = Math.max(this.pStart.getY(), this.pEnd.getY());
        double otherMaxY = Math.max(other.pStart.getY(), other.pEnd.getY());
        double thisMinY = Math.min(this.pStart.getY(), this.pEnd.getY());
        double otherMinY = Math.min(other.pStart.getY(), other.pEnd.getY());

        double thisMaxX = Math.max(this.pStart.getX(), this.pEnd.getX());
        double otherMaxX = Math.max(other.pStart.getX(), other.pEnd.getX());
        double thisMinX = Math.min(this.pStart.getX(), this.pEnd.getX());
        double otherMinX = Math.min(other.pStart.getX(), other.pEnd.getX());

        if (thisLineKind == 'v' && otherLineKind == 'v') {
            return null; // does'nt intresct
        }
        if (thisLineKind == 'r' && otherLineKind == 'v') {
            double m = calcSlope(this.pStart, this.pEnd);
            // find intersrction using the formula: y = m(x-x1)+y1
            double y = m * (other.pStart.getX() - this.pStart.getX()) + this.pStart.getY();
            Point p = new Point(other.pStart.getX(), y); // intersection point
            // added in ass3 to avoid bugs
            p.setX(digitPrecision(p.getX(), 3));
            p.setY(digitPrecision(p.getY(), 3));
            // check if the interserction within the "legal" area on - the
            // "limted" lines
            if (thisMinX <= p.getX() && p.getX() <= thisMaxX) {
                if (thisMinY <= p.getY() && p.getY() <= thisMaxY) {
                    if (otherMinX <= p.getX() && p.getX() <= otherMaxX) {
                        if (otherMinY <= p.getY() && p.getY() <= otherMaxY) {
                            return p;
                        }
                    }
                }
            }
            return null; // default - doesn't intersect
        }
        if (thisLineKind == 'v' && otherLineKind == 'r') {
            double m = calcSlope(other.pStart, other.pEnd);
            // find intersrction using the formula: y = m(x-x1)+y1
            double y = m * (this.pStart.getX() - other.pStart.getX()) + other.pStart.getY();
            Point p = new Point(this.pStart.getX(), y);
            // added in ass3 to avoid bugs
            p.setX(digitPrecision(p.getX(), 3));
            p.setY(digitPrecision(p.getY(), 3));
            // check if the interserction within the "legal" area on - the
            // "limted" lines
            if (thisMinX <= p.getX() && p.getX() <= thisMaxX) {
                if (thisMinY <= p.getY() && p.getY() <= thisMaxY) {
                    if (otherMinX <= p.getX() && p.getX() <= otherMaxX) {
                        if (otherMinY <= p.getY() && p.getY() <= otherMaxY) {
                            return p;
                        }
                    }
                }
            }
            return null; // default - doesn't intersect
        }
        if (thisLineKind == 'r' && otherLineKind == 'r') {
            // find slope = m
            double mOther = calcSlope(other.pStart, other.pEnd);
            double mThis = calcSlope(this.pStart, this.pEnd);
            // if has the same slope
            if (mOther == mThis) {
                return null; // doesn't intersect
            }
            /*
             * using the formula: y = m(x-x1)+y1 we are looking for x of both
             * intersection so the formula is: x = (m1x1-y1-m2x2+y2)/(m1-m2) y =
             * m1(x-x1)+y1
             */
            double m1x1 = mThis * this.pStart.getX();
            double y1 = this.pStart.getY();
            double m2x2 = mOther * other.pStart.getX();
            double y2 = other.pStart.getY();
            double m1 = mThis;
            double m2 = mOther;
            double x = (m1x1 - y1 - m2x2 + y2) / (m1 - m2);
            double y = m1 * (x - this.pStart.getX()) + y1;
            Point p = new Point(x, y);
            // added in ass3 to avoid bugs
            p.setX(digitPrecision(p.getX(), 3));
            p.setY(digitPrecision(p.getY(), 3));
            // check if the interserction within the "legal" area on - the
            // "limted" lines
            if (thisMinX <= p.getX() && p.getX() <= thisMaxX) {
                if (thisMinY <= p.getY() && p.getY() <= thisMaxY) {
                    if (otherMinX <= p.getX() && p.getX() <= otherMaxX) {
                        if (otherMinY <= p.getY() && p.getY() <= otherMaxY) {
                            return p;
                        }
                    }
                }
            }
            return null; // default - doesn't intersect
        }
        // default should never be reached (each line should be "regular" = r or
        // "vertical" = v
        return null;
    }

    /**
     * Checks if line intersects with the rectangle, if it does
     * return the closest intersection point to the start of the
     * line. if it doesn't return null.
     * @param rect - Rectangle object
     * @return p - the closest intersection point or null
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        // get a list with all the intersection points of this line & rect
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        // find the point with the minDistance from start of the line.
        double minDistance = intersectionPoints.get(0).distance(this.pStart);
        int indexOfMinDistancePoint = 0;
        for (int i = 0; i < intersectionPoints.size(); i++) {
            double distance = intersectionPoints.get(i).distance(this.pStart);
            if (distance < minDistance) {
                minDistance = distance;
                indexOfMinDistancePoint = i;
            }
        }
        return intersectionPoints.get(indexOfMinDistancePoint);
    }

    /**
     * returns a char representing the line kind.
     * @param p1 - Point (Line start Point)
     * @param p2 - Point (Line end Point)
     * @return - char, 'r' - regular line, 'v' - vertical line, 'e' - for error
     */
    private char lineKind(Point p1, Point p2) {
        // return 'r' - regular line, 'v' - vertical line, 'e' - for error
        double dy = p1.getY() - p2.getY();
        double dx = p1.getX() - p2.getX();
        if (dy == 0 & dx == 0) {
            System.out.println("its a point and not a line!");
            return 'e';
        }
        if (dx == 0 && dy != 0) {
            return 'v';
        }
        return 'r';
    }

    /**
     * calculates the slope 'm' between two points.
     * @param p1 - Point
     * @param p2 - Point
     * @return double slope 'm' between two points
     */
    private double calcSlope(Point p1, Point p2) {
        // find m (slope) of line
        double dy = p1.getY() - p2.getY();
        double dx = p1.getX() - p2.getX();
        return (dy / dx);
    }

    /**
     * return true if the lines are equal, false otherwise.
     * @param other - Line object
     * @return true/false if this and other Line are equal
     */
    public boolean equals(Line other) {
        if (this.pStart.getX() == other.pStart.getX()) {
            if (this.pStart.getY() == other.pStart.getY()) {
                if (this.pEnd.getX() == other.pEnd.getX()) {
                    if (this.pEnd.getY() == other.pEnd.getY()) {
                        return true;
                    }
                }
            }
        }
        if (this.pStart.getX() == other.pEnd.getX()) {
            if (this.pStart.getY() == other.pEnd.getY()) {
                if (this.pEnd.getX() == other.pStart.getX()) {
                    if (this.pEnd.getY() == other.pStart.getY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to round digits to and remain as precise as possible.
     * round the number up to given places, exp. digitPrecision(200.3456, 2) - returns 200.35
     * @param value - the number to round
     * @param places - number of digits to round
     * @return the nubmer in the requested format
     */
    private double digitPrecision(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
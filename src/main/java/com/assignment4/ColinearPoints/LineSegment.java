package com.assignment4.ColinearPoints;

public class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }

    
    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * For debugging;
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Do not use hashing here!
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }

    /**
     * Check if the two LineSegment objects are equal to each other by comparing their first and second points against each other.
     * @param lineSegment the other LineSegment object to be compared
     * @return true or false
     */
    public boolean isEqual(LineSegment lineSegment) {
        return (p.compareTo(lineSegment.p) == 0 && q.compareTo(lineSegment.q) == 0) || (p.compareTo(lineSegment.q) == 0 && q.compareTo(lineSegment.p) == 0);
    }
}


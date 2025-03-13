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
}


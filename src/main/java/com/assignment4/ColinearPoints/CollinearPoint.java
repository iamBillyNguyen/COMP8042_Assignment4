package com.assignment4.ColinearPoints;

// Helper data object to assign points and the slopes they make with an origin
public class CollinearPoint implements Comparable<CollinearPoint> {
    private final Point point;
    private final Double slope;

    public CollinearPoint(Point point, Double slope) {
        this.point = point;
        this.slope = slope;
    }

    public Point getPoint() {
        return point;
    }

    public Double getSlope() {
        return slope;
    }

    @Override
    // Compare points based on the slopes they make with an origin
    public int compareTo(CollinearPoint o) {
        int slopeCompare = Double.compare(this.slope, o.slope);
        if (slopeCompare == 0) {
            return this.point.compareTo(o.point);
        } else {
            return slopeCompare;
        }
    }
}

package com.assignment4.ColinearPoints;

import java.util.*;

public class ColinearRunner {
    public static void main(String[] args) {
        Point[] points = PointsReader.readPoints("data/points40.txt");

        BruteForceColinear brute = new BruteForceColinear(points);
        FastColinear fast = new FastColinear(points);
        List<LineSegment> segments = brute.getMaximalColinearLineSegments();
        System.out.println("Computed by brute force: ");
        System.out.println(segments);

        List<LineSegment> otherSegments = fast.getMaximalColinearLineSegments();
        System.out.println("Computed efficiently");
        System.out.println(otherSegments);

        // Draw the segments (if you want!)
        Point maxPoint = brute.maxPoint();
        System.out.println("Max point: " + maxPoint);
        StdDraw.setXscale(0, maxPoint.getX());
        StdDraw.setYscale(0, maxPoint.getY());
        brute.showSegments();
        StdDraw.show();
    }
}

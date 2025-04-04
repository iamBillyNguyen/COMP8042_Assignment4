package com.assignment4.ColinearPoints;

import java.util.*;

public class BruteForceColinear {
    private Point[] points;
    private List<LineSegment> calculatedMaximalColinearLineSegments;
    private enum Computed{
        YES, NO
    }
    private Computed computed;

    public BruteForceColinear(Point[] points){
        this.points = points;
        computed = Computed.NO;
    }    

    public int numberOfSegments(){
        return getMaximalColinearLineSegments().size();
    }        

    // Brute force algorithm to find all maximal colinear line segments
    public List<LineSegment> getMaximalColinearLineSegments(){
        // If already computed, just return the computed result
        if (computed == Computed.YES) {
            return calculatedMaximalColinearLineSegments;
        }
        
        calculatedMaximalColinearLineSegments = new ArrayList<>();
        List<LineSegment> collinearLineSegments = new ArrayList<>();

        // Iterate through every possible combination of 4 points
        // Time complexity of O(n^4)
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        // Vertical collinear when all points having the same x value, OR
                        // Horizontal collinear when points having the same y value, OR
                        // Collinear when points, with an anchor point, having the same slope values
                        if ((points[i].getX() == points[j].getX() && points[j].getX() == points[k].getX() && points[k].getX() == points[m].getX())
                                || (points[i].getY() == points[j].getY() && points[j].getY() == points[k].getY() && points[k].getY() == points[m].getY())
                                || (points[i].slopeOrder().compare(points[j], points[k]) == 0 &&
                                points[i].slopeOrder().compare(points[k], points[m]) == 0)) {

                            // Add to array
                            Point[] collinearPoints = {points[i], points[j], points[k], points[m]};
                            // Sort to get min and max points - Time complexity of O(nlogn)
                            Arrays.sort(collinearPoints);

                            // Add new line segment created by the min and max points from the combination
                            collinearLineSegments.add(new LineSegment(collinearPoints[0], collinearPoints[3]));
                        }
                    }
                }
            }
        }

        computed = Computed.YES;
        // To remove duplicated and reversed line segments
        // This will add O(n^2) to the total time complexity of the algorithm
        getUniqueLineSegments(collinearLineSegments);

        // Total time complexity without removing duplicated and reversed line segments is O(n^4)
        // Total time complexity with sorting and unique line segments is O(n^5*log(n))
        return calculatedMaximalColinearLineSegments;
    }

    /**
     * Get only unique line segments and add to calculatedMaximalColinearLineSegments by removing duplicated
     * and reversed line segments from temporary line segment list
     * @param collinearLineSegments temporary list of LineSegment
     */
    public void getUniqueLineSegments(List<LineSegment> collinearLineSegments) {
        // Time Complexity O(n^2)
        for (int i = 0; i < collinearLineSegments.size(); i++) {
            LineSegment lineSegment = collinearLineSegments.get(i);
            boolean isDuplicated = false;
            for (int j = 0; j < calculatedMaximalColinearLineSegments.size(); j++) {
                if (lineSegment.isEqual(calculatedMaximalColinearLineSegments.get(j))) {
                    isDuplicated = true;
                    break;
                }
            }
            if (!isDuplicated) {
                calculatedMaximalColinearLineSegments.add(lineSegment);
            }
        }
    }

    public void showSegments(){
        if(computed == Computed.NO){
            getMaximalColinearLineSegments();
        }
        for(LineSegment segment : calculatedMaximalColinearLineSegments){
            segment.draw();
        }
    }

    public Point maxPoint(){
        int maxX = points[0].getX();
        int maxY = points[0].getY();
        for(int i = 1; i < points.length; i++){
            if(points[i].getX() > maxX){
                maxX = points[i].getX();
            }
            if(points[i].getY()> maxY){
                maxY = points[i].getY();
            }
        }
        return new Point(maxX, maxY);
    }
}

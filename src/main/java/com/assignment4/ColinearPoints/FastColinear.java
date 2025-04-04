package com.assignment4.ColinearPoints;

import java.util.*;
import com.assignment4.Sorting.QuickInsertionSort;

public class FastColinear {
    private Point[] points;
    private List<LineSegment> calculatedMaximalColinearLineSegments;
    QuickInsertionSort<CollinearPoint> sorter = new QuickInsertionSort<>();
    private enum Computed{
        YES, NO
    }
    private Computed computed;

    public FastColinear(Point[] points){
        this.points = points;
        computed = Computed.NO;
    }    

    public int numberOfSegments(){
        return getMaximalColinearLineSegments().size();
    }        

    // Efficient force algorithm to find all maximal colinear line segments
    public List<LineSegment> getMaximalColinearLineSegments(){
        // If already computed, just return the computed result
        if (computed == Computed.YES) {
            return calculatedMaximalColinearLineSegments;
        }

        // Space complexity of O(number of line segments)
        calculatedMaximalColinearLineSegments = new ArrayList<>();
        // Space complexity of O(n)
        CollinearPoint[] collinearPointArray = new CollinearPoint[points.length];
        // Space complexity of O(n)
        List<Point> collinearPointList = new ArrayList<>();
        // Space complexity of O(n)
        List<LineSegment> collinearLineSegments = new ArrayList<>();

        // Time complexity of O(n) * O(n + n + nlog(n))
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            collinearPointList.add(p);

            // Time complexity of O(n)
            for (int j = 0; j < points.length; j++) {
                collinearPointArray[j] = new CollinearPoint(points[j], p.slopeTo(points[j]));
            }

            // Time complexity of O(n*log(n)) (presumably)
            sorter.quickInsertionSort(collinearPointArray);

            // Count number of points within the same slope
            int count = 1; // First slope with origin contains 1 point

            // Time complexity of O(n)
            for (int k = 1; k < collinearPointArray.length; k++) {
                // Check if the slopes of adjacent and sorted points are equal
                if (Double.compare(collinearPointArray[k].getSlope(), collinearPointArray[k - 1].getSlope()) == 0) {
                    collinearPointList.add(collinearPointArray[k].getPoint());
                    collinearPointList.add(collinearPointArray[k-1].getPoint());
                    count++;
                } else {
                    // If any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p
                    if (count >= 3) {
                        // Create new line segment using the min and max points from collinearPointList to avoid sub-sets being added
                        LineSegment lineSegment = new LineSegment(Collections.min(collinearPointList), Collections.max(collinearPointList));
                        collinearLineSegments.add(lineSegment);
                    }

                    // Reset count for next collinear combination
                    count = 1;
                    // Reset collinear point list and re-adding origin point to list
                    collinearPointList.clear();
                    collinearPointList.add(p);
                }
            }

            // The last element made the final collinear combination
            if (count >= 3) {
                LineSegment lineSegment = new LineSegment(Collections.min(collinearPointList), Collections.max(collinearPointList));
                collinearLineSegments.add(lineSegment);
            }
        }

        computed = Computed.YES;
        // Time Complexity O(n^2). This function adds O(n^2) to the overall algorithm to achieve a unique final array
        // Without this function, my algorithm achieves the expected O(n^2*log(n)) time complexity but the final array will contain duplicated and reversed line segments.
        getUniqueLineSegments(collinearLineSegments);

        // Total space complexity of O(n) + O(n) + O(n) + O(number of line segments) = O(n + number of line segments)
        // Total time complexity of O(n) + O(n + n + nlog(n)) + O(n^2)= O(n^2) with only unique line segments
        // Total time complexity of O(n) + O(n + n + nlog(n)) = O(n^2*log(n)) without removing duplicates and reversed line segment
        return calculatedMaximalColinearLineSegments;
    }

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
        //Make sure segments have been computed before drawing
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

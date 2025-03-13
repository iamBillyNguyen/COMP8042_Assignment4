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

        /*
         * Your code here
         */
        computed = Computed.YES;
        return calculatedMaximalColinearLineSegments;
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

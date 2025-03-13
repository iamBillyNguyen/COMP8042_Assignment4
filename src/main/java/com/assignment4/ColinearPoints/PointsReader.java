package com.assignment4.ColinearPoints;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PointsReader {
    
    public static Point[] readPoints(String filename){
        //read the file 
        File file = new File(filename);
    
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }

        int numberOfPoints = scanner.nextInt();
        Point[] points = new Point[numberOfPoints];

        for(int i = 0; i < numberOfPoints; i++){
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[i] = new Point(x, y);
        }
        scanner.close();

        return points;
    }
}

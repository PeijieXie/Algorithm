import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 * @author xiepeijie
 */
public class BruteCollinearPoints {
    
    private final LineSegment[] segments;
   
   // finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
       
       if (points == null) {
           throw new java.lang.IllegalArgumentException();
       }
            
       int pointCount = points.length;
       
       for (int i = 0; i < pointCount; i++) {
            if(points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int j = i + 1; j < pointCount; j++) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
       
       Point[] sortedPoints = points.clone();
       Arrays.sort(sortedPoints);       
       
       ArrayList<LineSegment> lineSegments = new ArrayList<>();
       
       for (int i = 0; i < pointCount - 3; i++) {
           for (int j = i + 1; j < pointCount - 2; j++){
               for (int k = j + 1; k < pointCount - 1; k++){
                   for (int l = k + 1; l < pointCount; l++){
                       //find line segments containing 4 points
                       if (sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[k])
                               && sortedPoints[i].slopeTo(sortedPoints[j]) == sortedPoints[i].slopeTo(sortedPoints[l])) {
                           lineSegments.add(new LineSegment(sortedPoints[i], sortedPoints[l]));
                       }
                   }
               }
           }
       }
       segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);
   }
   
   // the number of line segments
   public int numberOfSegments() {        
       return segments.length;
   }
   
   // the line segments
   public LineSegment[] segments() {
       return segments.clone();
   }
}


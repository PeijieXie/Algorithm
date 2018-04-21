import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author xiepeijie
 */
public class FastCollinearPoints {
    
    private LineSegment[] segments;
    
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) {
           throw new java.lang.IllegalArgumentException();
        }
        
        int pointCount = points.length;
        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        // check duplicate points or null points
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
        
        // copy points to be ordered by slope
        Point[] copyPoints = points.clone();
        // temporaly save the points on the same line
        ArrayList<Point> lineSegmentsPoints = new ArrayList<>();
        Point[] SegmentsPoints;
        
        for (int i = 0; i < pointCount; i++) {
            lineSegmentsPoints.clear();
            Arrays.sort(copyPoints, points[i].slopeOrder());
            double lastSlope = Double.NEGATIVE_INFINITY;
            double thisSlope;
            for (int j = 1; j < pointCount; j++) {                
                thisSlope = copyPoints[j].slopeTo(points[i]);
                if ((thisSlope != lastSlope || j == pointCount - 1) && lineSegmentsPoints.size() >= 3)  {
                    SegmentsPoints = lineSegmentsPoints.toArray(new Point[lineSegmentsPoints.size()]);
                    Arrays.sort(SegmentsPoints);
                    lineSegments.add(new LineSegment(SegmentsPoints[0], SegmentsPoints[lineSegmentsPoints.size() - 1]));
                    lastSlope = thisSlope;
                    lineSegmentsPoints.clear();
                }
                else if (thisSlope != lastSlope) {
                    lastSlope = thisSlope;
                    lineSegmentsPoints.clear();
                    lineSegmentsPoints.add(copyPoints[i]);
                    lineSegmentsPoints.add(copyPoints[j]);
                }
                else if (thisSlope == lastSlope) {
                    lineSegmentsPoints.add(copyPoints[j]);
                }
            }
        }
        HashSet<LineSegment> hs = new HashSet<>();
        hs.addAll(lineSegments);
        lineSegments.clear();
        lineSegments.addAll(hs);
        segments = lineSegments.toArray(new LineSegment[lineSegments.size()]);

    }     
    
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }       
    
    // the line segments
    public LineSegment[] segments() {
        return segments;
    } 
    
    
    
    
}

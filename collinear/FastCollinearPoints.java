
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author xiepeijie
 */
public class FastCollinearPoints {
    
    private final LineSegment[] segments;
    
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        
        // check null input
        if (points == null) {
           throw new java.lang.IllegalArgumentException();
        }
        
        int pointCount = points.length;

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
        
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        // copy points to be ordered by slope
        Point[] copyPoints = points.clone();
        Arrays.sort(copyPoints);
        Point[] copyPointsOrderedByP = points.clone();
        // temporaly save the points on the same line
        ArrayList<Point> lineSegmentsPoints = new ArrayList<>();
        Point[] SegmentsPoints;
        double lastSlope;
        double thisSlope;
        for (int i = 0; i < pointCount; i++) {
            lineSegmentsPoints.clear();
            lastSlope = Double.NEGATIVE_INFINITY;
            Arrays.sort(copyPointsOrderedByP, copyPoints[i].slopeOrder());
            // start from j = 1, do not compare points with itself
            for (int j = 1; j < pointCount; j++) {
                thisSlope = copyPointsOrderedByP[j].slopeTo(copyPoints[i]);
                if (Double.compare(thisSlope, lastSlope) == 0) {
                    lineSegmentsPoints.add(copyPointsOrderedByP[j]);
                }
                if ((Double.compare(thisSlope, lastSlope) != 0 || j == pointCount - 1) 
                        && lineSegmentsPoints.size() >= 4) {
                    SegmentsPoints = lineSegmentsPoints.toArray(new Point[lineSegmentsPoints.size()]);
                    Arrays.sort(SegmentsPoints);
                    // avoid duplicate line segments
                    if (copyPoints[i].compareTo(SegmentsPoints[0]) == 0) {
                        lineSegments.add(new LineSegment(SegmentsPoints[0], SegmentsPoints[lineSegmentsPoints.size() - 1]));
                    }
                    lastSlope = thisSlope;
                    lineSegmentsPoints.clear();
                    lineSegmentsPoints.add(copyPoints[i]);
                    lineSegmentsPoints.add(copyPointsOrderedByP[j]);
                }
                else if (Double.compare(thisSlope, lastSlope) != 0) {
                    lastSlope = thisSlope;
                    lineSegmentsPoints.clear();
                    lineSegmentsPoints.add(copyPoints[i]);
                    lineSegmentsPoints.add(copyPointsOrderedByP[j]);
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
       
    public static void main(String[] args) {
        
        int n = 8;
        Point[] points = new Point[n];
        points[0] = new Point(1, 5);
        points[1] = new Point(2, 5);
        points[2] = new Point(3, 5);
        points[3] = new Point(4, 5);
        points[4] = new Point(5, 5);
        points[5] = new Point(5, 6);
        points[6] = new Point(5, 7);
        points[7] = new Point(5, 8);
        //points[8] = new Point(10, 10);
        
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}

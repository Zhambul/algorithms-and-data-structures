import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Closest {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static double minimalDistance(int[] x, int y[]) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            points.add(new Point(x[i], y[i]));
        }

        points.sort(Comparator.comparingInt(p -> p.x));
        return minDistance(points);
    }

    private static double minDistance(List<Point> points) {
        if (points.size() < 2) {
            return Double.POSITIVE_INFINITY;
        }

        double maxX = points.get(0).x;
        double minX = points.get(points.size() - 1).x;

        double xDivisor = (maxX + minX) / 2;

        if (minX == maxX) {
            double minDist = Double.POSITIVE_INFINITY;
            points.sort(Comparator.comparingInt(p -> p.y));
            for (int i = 0; i < points.size() - 1; i++) {
                minDist = Math.min(minDist, Math.abs(points.get(i).y - points.get(i + 1).y));
            }
            return minDist;
        }

        List<Point> leftSide = new ArrayList<>();
        List<Point> rightSide = new ArrayList<>();

        for (Point point : points) {
            if (point.x < xDivisor) {
                leftSide.add(point);
            } else {
                rightSide.add(point);
            }
        }

        double d1 = minDistance(leftSide);
        double d2 = minDistance(rightSide);

        double d = Math.min(d1, d2);

        if (d == 0) {
            return 0;
        }

        double divisorLeft = xDivisor - d;
        double divisorRight = xDivisor + d;
        List<Point> stripPoints = new ArrayList<>();
        for (Point p : points) {
            if (p.x > divisorLeft && p.x < divisorRight) {
                stripPoints.add(p);
            }
        }
        stripPoints.sort(Comparator.comparingInt(p -> p.y));

        double stripMinDistance = d;

        if (stripPoints.size() > 1) {
            for (int i = 0; i < stripPoints.size(); i++) {
                int l = Math.max(i - 7, 0);
                int r = Math.min(i + 7, stripPoints.size() - 1);
                for (int j = l; j <= r; j++) {
                    if (i == j) {
                        continue;
                    }
                    Point p1 = stripPoints.get(i);
                    Point p2 = stripPoints.get(j);
                    if (Math.abs(p1.y - p2.y) < stripMinDistance) {
                        double dist = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
                        stripMinDistance = Math.min(stripMinDistance, dist);
                    }
                }
            }
        }

        return Math.min(stripMinDistance, d);
    }

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new PrintWriter(System.out);
        int n = nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = nextInt();
            y[i] = nextInt();
        }
        System.out.println(minimalDistance(x, y));
        writer.close();
    }

    static BufferedReader reader;
    static PrintWriter writer;
    static StringTokenizer tok = new StringTokenizer("");


    static String next() {
        while (!tok.hasMoreTokens()) {
            String w = null;
            try {
                w = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (w == null)
                return null;
            tok = new StringTokenizer(w);
        }
        return tok.nextToken();
    }

    static int nextInt() {
        return Integer.parseInt(next());
    }

    static long nextLong() {
        return Long.parseLong(next());
    }
}

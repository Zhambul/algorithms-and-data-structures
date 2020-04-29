import java.util.*;

public class PointsAndSegments {

    static class Segment {
        int start;
        int end;

        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class Point {
        int value;
        int index;

        public Point(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    static int[] cnt;

    private static int[] fastCountSegments(int[] starts, int[] ends, int[] ps) {
        cnt = new int[ps.length];

        List<Segment> segmentsSortedStart = new ArrayList<>();
        List<Segment> segmentsSortedEnd = new ArrayList<>();

        for (int i = 0; i < starts.length; i++) {
            segmentsSortedStart.add(new Segment(starts[i], ends[i]));
            segmentsSortedEnd.add(new Segment(starts[i], ends[i]));
        }

        segmentsSortedStart.sort(Comparator.comparingInt(s -> s.start));
        segmentsSortedEnd.sort(Comparator.comparingInt(s -> s.end));

        for (int i = 0; i < ps.length; i++) {
            int segStartI = 0;
            while (segmentsSortedStart.get(segStartI).start <= ps[i]) {
                segStartI++;
            }
            int segEndI = 0;
            while (segmentsSortedEnd.get(segEndI).start <= ps[i]) {
                segStartI++;
            }
        }
        //        List<Point> points = new ArrayList<>();
//
//        for (int i = 0; i < ps.length; i++) {
//            points.add(new Point(ps[i], i));
//        }
//
//        points.sort(Comparator.comparingInt(p -> p.value));
//
////        for (int i = 0; i < segments.size(); i++) {
////            binarySearch(segments.get(i), points);
////        }
//
//
//        int lastSegmentStart = 0;
//
////
//        for (int i = 0; i < points.size(); i++) {
//            int l = (lastSegmentStart + segments.size()) / 2;
//            int r = segments.size();
//            for (int j = lastSegmentStart; j < segments.size(); j++) {
//                Segment s = segments.get(j);
//                if (s.end < points.get(i).value) {
//                    lastSegmentStart = j;
//                }
//                if (s.start <= points.get(i).value && points.get(i).value <= s.end) {
//                    cnt[points.get(i).index]++;
//                } else {
//                    break;
//                }
//            }
//        }
//        segments.forEach(s -> {
//            for (int i = 0; i < points.length; i++) {
//                if (s.start <= points[i] && points[i] <= s.end) {
//                    cnt[i]++;
//                } else {
//                    break;
//                }
//            }
//        });
        return cnt;
    }

    private static void binarySearch(Segment segment, List<Point> points) {
        _binarySearch(segment, points, 0, points.size() - 1);
    }

    private static void _binarySearch(Segment segment, List<Point> points, int l, int r) {
        if (l > r) {
            return;
        }
        int m = (l + r) / 2;
        Point point = points.get(m);

        if (segment.start <= point.value && point.value <= segment.end) {
            cnt[point.index]++;
            _binarySearch(segment, points, l, m - 1);
            _binarySearch(segment, points, m + 1, r);
        } else if (segment.start < point.value) {
            _binarySearch(segment, points, l, m - 1);
        } else if (segment.end > point.value) {
            _binarySearch(segment, points, m + 1, r);
        }
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
        int[] cnt = fastCountSegments(starts, ends, points);
        for (int x : cnt) {
            System.out.print(x + " ");
        }
    }
}


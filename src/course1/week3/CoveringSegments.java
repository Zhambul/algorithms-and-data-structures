import java.util.*;

public class CoveringSegments {

    private static int[] optimalPoints(Segment[] s) {
        //write your code here
        List<Segment> segments = new ArrayList<>(Arrays.asList(s));
        List<Integer> points = new ArrayList<>();

        segments.sort(Comparator.comparing((Segment segment) -> segment.end));

        while (!segments.isEmpty()) {
            Segment segment = segments.remove(0);
            points.add(segment.end);
            Iterator<Segment> iterator = segments.iterator();
            while (iterator.hasNext()) {
                Segment next = iterator.next();
                if (next.start <= segment.end && next.end >= segment.end) {
                    iterator.remove();
                } else if(next.start > segment.end) {
                    break;
                }
            }
        }

        return points.stream().mapToInt(it -> it).toArray();
    }

    private static class Segment {
        int start, end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        int[] points = optimalPoints(segments);
        System.out.println(points.length);
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}


import java.util.*;
import java.util.function.ToIntFunction;

public class Dijkstra {

    static class NodePair {
        final Node left;
        final Node right;

        NodePair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    static class Node {
        boolean visited;
        int value;

        public Node(int value) {
            this.value = value;
        }

        List<Node> next = new ArrayList<>();
        Map<Node, Integer> weigh = new HashMap<>();
        List<Node> prev = new ArrayList<>();

        @Override
        public String toString() {
            return String.valueOf(value + 1);
        }
    }

    static Map<Integer, Node> map = new HashMap<>();

    private static int getMinimumDistance(int st, int end) {
        Node start = map.get(st);
        Map<Node, Integer> dist = initDist(start);
        PriorityQueue<Node> q = initQueue(start, dist);

        while (!q.isEmpty()) {
            Node minNode = q.poll();
            minNode.visited = true;
            for (Node next : minNode.next) {
                if (relax(minNode, next, dist));
                if (!next.visited) {
                    next.visited = true;
                    q.add(next);
                }
            }
        }

        return getDistance(end, dist);
    }

    private static int getDistance(int end, Map<Node, Integer> dist) {
        Integer d = dist.get(map.get(end));
        if (d == Integer.MAX_VALUE) {
            return -1;
        }
        return d;
    }

    private static PriorityQueue<Node> initQueue(Node start, Map<Node, Integer> dist) {
        PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(dist::get));
        q.add(start);
        return q;
    }

    private static Map<Node, Integer> initDist(Node start) {
        Map<Node, Integer> dist = new HashMap<>();
        for (Map.Entry<Integer, Node> e : map.entrySet()) {
            dist.put(e.getValue(), Integer.MAX_VALUE);
        }
        dist.put(start, 0);
        return dist;
    }

    private static boolean relax(Node minNode, Node next, Map<Node, Integer> dist) {
        if (dist.get(next) > dist.get(minNode) + minNode.weigh.get(next)) {
            dist.put(next, dist.get(minNode) + minNode.weigh.get(next));
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;
            int w = scanner.nextInt();

            Node left = map.get(x);
            if (left == null) {
                left = new Node(x);
                map.put(x, left);
            }
            Node right = map.get(y);
            if (right == null) {
                right = new Node(y);
                map.put(y, right);
            }
            left.next.add(right);
            left.weigh.put(right, w);
            right.prev.add(left);
        }

        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, new Node(i));
            }
        }
        int s = scanner.nextInt() - 1;
        int e = scanner.nextInt() - 1;
        System.out.println(getMinimumDistance(s, e));
    }
}


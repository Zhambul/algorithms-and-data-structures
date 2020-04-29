import java.util.*;

public class BFS {

    static class Node {
        boolean visited;
        int cluster;
        int value;

        public Node(int value) {
            this.value = value;
        }

        List<Node> neighs = new ArrayList<>();

        @Override
        public String toString() {
            return String.valueOf(value + 1);
        }
    }

    static Map<Integer, Node> map = new HashMap<>();

    private static int getDistance(int s, int end) {
        Node start = map.get(s);
        Queue<Node> q = new ArrayDeque<>();
        Map<Node, Integer> distance = new HashMap<>();
        q.add(start);
        distance.put(start, 0);

        while (!q.isEmpty()) {
            Node node = q.poll();
            node.visited = true;
            for (Node neigh : node.neighs) {
                if (!neigh.visited) {
                    neigh.visited = true;
                    q.add(neigh);
                    distance.put(neigh, distance.get(node) + 1);
                    if (neigh.value == end) {
                        return distance.get(neigh);
                    }
                }
            }
        }

        Integer d = distance.get(map.get(end));
        if (d == null) {
            return -1;
        }
        return d;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt() - 1;
            int y = scanner.nextInt() - 1;

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
            left.neighs.add(right);
            right.neighs.add(left);
        }
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, new Node(i));
            }
        }
        int u = scanner.nextInt() - 1;
        int v = scanner.nextInt() - 1;
        System.out.println(getDistance(u, v));
    }
}


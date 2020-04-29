import java.util.*;

public class ConnectedComponents {

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
    static int CLUSTERS = 1;

    private static int countClusters() {
        while (!map.isEmpty()) {
            Node node = map.values().iterator().next();
            explore(node);
            CLUSTERS++;
        }
        return CLUSTERS - 1;
    }

    private static void explore(Node node) {
        node.visited = true;
        node.cluster = CLUSTERS;
        for (Node n : node.neighs) {
            if (!n.visited) {
                explore(n);
            }
        }
        map.remove(node.value);
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
        System.out.println(countClusters());
    }
}


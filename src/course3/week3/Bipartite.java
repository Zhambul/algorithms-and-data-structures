import java.util.*;

public class Bipartite {

    static enum Color {
        BLACK, RED;

        Color inverted() {
            switch (this) {
                case BLACK:
                    return RED;
                case RED:
                    return BLACK;
            }
            return null;
        }
    }

    static class Node {
        boolean visited;
        int cluster;
        int value;
        Color color;


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

    private static boolean isBipartite() {
        Node start = map.entrySet().iterator().next().getValue();
        start.color = Color.BLACK;
        Queue<Node> q = new ArrayDeque<>();
        q.add(start);

        while (!q.isEmpty()) {
            Node node = q.poll();
            node.visited = true;
            for (Node neigh : node.neighs) {
                if (neigh.color != null && neigh.color == node.color) {
                    return false;
                }
                if (neigh.color == null) {
                    neigh.color = node.color.inverted();
                }
                if (!neigh.visited) {
                    neigh.visited = true;
                    q.add(neigh);
                }
            }
        }
        return true;
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
        if (isBipartite()) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}


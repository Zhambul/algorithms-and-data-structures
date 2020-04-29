import java.util.*;

public class Reachability {

    static class Node {
        boolean visited;
        int value;

        public Node(int value) {
            this.value = value;
        }

        List<Node> neighs = new ArrayList<>();
    }

    static Map<Integer, Node> map = new HashMap<>();

    private static boolean reach(int x, int y) {
        Node left = map.get(x);
        if (left == null) {
            return false;
        }
        explore(left);
        Node right = map.get(y);
        if (right == null) {
            return false;
        }
        return right.visited;
    }

    private static void explore(Node node) {
        node.visited = true;
        for (Node n : node.neighs) {
            if (!n.visited) {
                explore(n);
            }
        }
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

        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        if (reach(x, y)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}


import java.util.*;
import java.util.stream.Collectors;

public class Asd {

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

    private static List<Integer> getDistancesFrom(int from) {
        Node node = map.get(from);

        Map<Node, Integer> dist = initDist(node);
        for (int i = 0; i < map.size(); i++) {
            if (relaxAll(dist).isEmpty()) {
                break;
            }
        }
        List<Node> nodesOfNegativeCycle = relaxAll(dist);
        for (Node n : nodesOfNegativeCycle) {
            dist.put(n, Integer.MIN_VALUE);
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {
            Node n = map.get(i);
            Integer d = dist.get(n);
            result.add(d);
        }
        return result;
    }

    private static List<Node> relaxAll(Map<Node, Integer> dist) {
        List<Node> relaxedNodes = new ArrayList<>();
        for (Map.Entry<Integer, Node> e : map.entrySet()) {
            Node n = e.getValue();
            for (Node next : n.next) {
                if (relax(n, next, dist)) {
                    relaxedNodes.add(next);
                    relaxedNodes.add(n);
                }
            }
        }
        return relaxedNodes;
    }

    private static Map<Node, Integer> initDist(Node start) {
        Map<Node, Integer> dist = new HashMap<>();
        for (Map.Entry<Integer, Node> e : map.entrySet()) {
            dist.put(e.getValue(), Integer.MAX_VALUE - 10000);
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
        int i = scanner.nextInt() - 1;
        List<Integer> distances = getDistancesFrom(i);
        for (Integer distance : distances) {
            if (distance == Integer.MIN_VALUE) {
                System.out.println("-");
            } else if (distance == Integer.MAX_VALUE - 10000) {
                System.out.println("*");
            } else {
                System.out.println(distance);
            }
        }
    }
}


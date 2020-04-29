import java.util.*;

public class NegativeCycle {

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

    private static boolean isNegativeCycle() {
        if (map.isEmpty()) {
            return false;
        }
        Node node = map.entrySet().iterator().next().getValue();

        Map<Node, Integer> dist = initDist(node);
        for (int i = 0; i < map.size(); i++) {
            if (!relaxAll(dist)) {
                break;
            }
        }
        return relaxAll(dist);
//        PriorityQueue<Node> q = initQueue(start, dist);
//
//        while (!q.isEmpty()) {
//            Node minNode = q.poll();
//            for (Node next : minNode.next) {
//                if (relax(minNode, next, dist)) {
//                    q.add(next);
//                }
//            }
//        }
//
//        return getDistance(end, dist);
    }

    private static boolean relaxAll(Map<Node, Integer> dist) {
        boolean relaxedAny = false;
        for (Map.Entry<Integer, Node> e : map.entrySet()) {
            Node n = e.getValue();
            for (Node next : n.next) {
                if (relax(n, next, dist)) {
                    relaxedAny = true;
                }
            }
        }
        return relaxedAny;
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
        if (dist.get(minNode) == Integer.MAX_VALUE) {
            return false;
        }
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
        if (isNegativeCycle()) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}


import java.util.*;
import java.util.stream.Collectors;

public class Toposort {

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
        List<Node> prev = new ArrayList<>();

        @Override
        public String toString() {
            return String.valueOf(value + 1);
        }
    }

    static Map<Integer, Node> map = new HashMap<>();

    private static List<Node> order() {
        LinkedList<Node> result = new LinkedList<>();
        while (!map.isEmpty()) {
            NodePair sink = getSink(map.entrySet().iterator().next().getValue());
            while (!map.isEmpty()) {
                result.addFirst(sink.right);
                remove(sink.right);
                sink = getSink(sink.left);
                if (sink == null) {
                    break;
                }
            }
        }
        return result;
    }

    private static void remove(Node node) {
        map.remove(node.value);
        for (Node prev : node.prev) {
            prev.next.remove(node);
        }
    }

    private static NodePair getSink(Node node) {
        if (!map.containsKey(node.value)) {
            return null;
        }
        if (node.next.isEmpty() && node.prev.isEmpty()) {
            return new NodePair(node, node);
        }

        if (node.next.isEmpty()) {
            return new NodePair(node.prev.get(0), node);
        }

        Node prev = node;
        Node next = node.next.get(0);
        Node sink;
        while (!next.next.isEmpty()) {
            prev = next;
            next = next.next.get(0);
        }
        sink = next;
        return new NodePair(prev, sink);
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
            left.next.add(right);
            right.prev.add(left);
        }

        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, new Node(i));
            }
        }

        System.out.println(order().stream().map((Node node) -> String.valueOf(node.value + 1)).collect(Collectors.joining(" ")));
    }
}


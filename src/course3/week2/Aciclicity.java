import java.util.*;

public class Aciclicity {

    static class Node {
        boolean visited;
        int value;
        int postNumber;

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
    static Map<Integer, Node> mapR;
    static Stack<Node> maxPost = new Stack<>();
    static int COUNTER = 1;

    private static boolean isCyclic() {
        mapR = invert(map);
        DFS(mapR);
        while (!maxPost.isEmpty()) {
            Node nodeOfSinkComponent = maxPost.pop();
            Node node = map.get(nodeOfSinkComponent.value);
            if (!node.next.isEmpty()) {
                return true;
            }
            //node is sink. need to delete it from
            for (Node prev : node.prev) {
                prev.next.remove(node);
            }
        }
        return false;
    }

    private static Map<Integer, Node> invert(Map<Integer, Node> map) {
        Map<Integer, Node> mapR = new HashMap<>();
        for (Map.Entry<Integer, Node> e : map.entrySet()) {
            Node n = e.getValue();
            invert(n, mapR);
        }
        return mapR;
    }

    private static Node invert(Node node, Map<Integer, Node> mapR) {
        Node nR = mapR.get(node.value);
        if (nR != null) {
            return nR;
        }
        nR = new Node(node.value);
        mapR.put(nR.value, nR);

        for (Node next : node.next) {
            Node nextR = mapR.get(next.value);
            if (nextR != null) {
                nR.prev.add(nextR);
                continue;
            }
            nR.prev.add(invert(next, mapR));
        }

        for (Node next : node.prev) {
            Node nextR = mapR.get(next.value);
            if (nextR != null) {
                nR.next.add(nextR);
                continue;
            }
            nR.next.add(invert(next, mapR));
        }
        return nR;
    }

    private static void DFS(Map<Integer, Node> toDfs) {
        Map<Integer, Node> map = new HashMap<>(toDfs);
        while (!map.isEmpty()) {
            explore(map.entrySet().iterator().next().getValue(), map);
        }
    }

    private static void explore(Node node, Map<Integer, Node> map) {
        node.visited = true;
        for (Node n : node.next) {
            if (!n.visited) {
                explore(n, map);
            }
        }
        node.postNumber = COUNTER;
        COUNTER++;
        map.remove(node.value);
        maxPost.add(node);
    }

    private static void exploreAndCollect(Node node, List<Node> collect) {
        for (Node n : node.next) {
            collect.add(n);
            exploreAndCollect(n, collect);
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
            left.next.add(right);
            right.prev.add(left);
        }

        for (int i = 0; i < n; i++) {
            if (!map.containsKey(i)) {
                map.put(i, new Node(i));
            }
        }

        if (isCyclic()) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}


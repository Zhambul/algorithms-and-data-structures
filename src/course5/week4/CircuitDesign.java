
import java.io.*;
import java.util.*;

public class CircuitDesign {
    private final InputReader reader;
    private final OutputWriter writer;

    public CircuitDesign(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CircuitDesign(reader, writer).run();
        writer.writer.flush();
    }

    class Clause {
        int first;
        int second;
    }

    class Node {
        final int value;
        public int post;
        boolean visited;
        boolean isMeta;

        List<Node> next = new ArrayList<>();
        List<Node> prev = new ArrayList<>();
        Set<Node> inner;

        Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    value +
                    '}';
        }
    }


    class TwoSatisfiability {
        int numVars;
        Clause[] clauses;
        Map<Integer, Node> map = new HashMap<>();
        Stack<Node> maxPost = new Stack<>();

        TwoSatisfiability(int n, int m) {
            numVars = n;
            clauses = new Clause[m];
            for (int i = 0; i < m; ++i) {
                clauses[i] = new Clause();
            }
        }

        boolean isSatisfiable(int[] result) {
            boolean[] assigned = new boolean[result.length];
            constructImplicationGraph();
            List<List<Node>> sccs = findSCCs();
            if (!isSatisfiable(sccs)) {
                return false;
            }
            List<Node> sortedMeta = toposort(toMeta(sccs));
            for (Node meta : sortedMeta) {
                for (Node node : meta.inner) {
                    int i = Math.abs(node.value) - 1;
                    if (!assigned[i]) {
                        assigned[i] = true;
//                        result[i] = node.value;
                        if (node.value < 0) {
                            result[i] = 1;
                        } else {
                            result[i] = 0;
                        }
                    }
                }
            }
            return true;
        }

        private List<Node> toposort(List<Node> sccs) {
            List<Node> nodes = new ArrayList<>();
            SinkRetriever sinkRetriever = new SinkRetriever(sccs);
            while (true) {
                Node sink = sinkRetriever.getSink();
                if (sink == null) {
                    break;
                }
                nodes.add(sink);
            }
            return nodes;
        }

        class SinkRetriever {
            final List<Node> nodes;
            Node prev;

            SinkRetriever(List<Node> nodes) {
                this.nodes = nodes;
            }

            Node getSink() {
                if (prev == null) {
                    return getNewSink();
                }

                return getNewSink(prev);
            }

            Node getNewSink() {
                if (nodes.isEmpty()) {
                    return null;
                }
                return getNewSink(nodes.get(0));
            }

            Node getNewSink(Node node) {
                Node sink;
                if (node.next.isEmpty()) {
                    sink = node;
                    if (!sink.prev.isEmpty()) {
                        prev = sink.prev.get(0);
                    } else {
                        prev = null;
                    }
                } else {
                    prev = node;
                    sink = getNewSink(node.next.get(0));
                }
                nodes.remove(sink);
                for (Node prev : sink.prev) {
                    prev.next.remove(sink);
                }
                return sink;
            }
        }

        private List<Node> toMeta(List<List<Node>> sccs) {
            List<Node> metaNodes = new ArrayList<>();
            for (List<Node> scc : sccs) {
                Node meta = new Node(Integer.MAX_VALUE);
                meta.isMeta = true;
                meta.inner = new HashSet<>();
                meta.inner.addAll(scc);
                metaNodes.add(meta);
            }
            for (Node metaNode : metaNodes) {
                for (Node inner : metaNode.inner) {
                    for (Node next : inner.next) {
                        if (!metaNode.inner.contains(next)) {
                            Node nextMeta = metaNodes.stream().filter(meta -> meta.inner.contains(next)).findFirst().orElse(null);
                            metaNode.next.add(nextMeta);
                        }
                    }
                }
            }
            for (Node meta : metaNodes) {
                for (Node next : meta.next) {
                    next.prev.add(meta);
                }
            }
            return metaNodes;
        }

        private boolean isSatisfiable(List<List<Node>> sccs) {
            for (List<Node> scc : sccs) {
                if (haveContradiction(scc)) {
                    return false;
                }
            }
            return true;
        }

        private boolean haveContradiction(List<Node> scc) {
            Set<Integer> set = new HashSet<>();
//            int prev = Integer.MAX_VALUE;
            for (Node node : scc) {
                Integer a = Math.negateExact(node.value);
                if (set.contains(a)) {
                    return true;
                }
                set.add(node.value);
//                if (prev == Integer.MAX_VALUE) {
//                    prev = node.value;
//                } else {
//                    if (prev < 0 && node.value > 0) {
//                        return true;
//                    }
//                    if (prev > 0 && node.value < 0) {
//                        return true;
//                    }
//                    prev = node.value;
//                }
            }
            return false;
        }

        private List<List<Node>> findSCCs() {
            List<List<Node>> result = new ArrayList<>();
            Map<Integer, Node> inverse = invertGraph();
            DFS(inverse);
            cleanVisited(inverse);
            Map<Integer, Node> copy = new HashMap<>(map);
            while (!maxPost.isEmpty()) {
                Node sinkComponentNode = maxPost.pop();
                sinkComponentNode = copy.get(sinkComponentNode.value);
                List<Node> sinkComponent = new ArrayList<>();
                exploreAndCollect(sinkComponentNode, sinkComponent, copy);
                for (Node node : sinkComponent) {
                    copy.remove(node.value);
                }
                for (int i = 0; i < sinkComponent.size() - 1; i++) {
                    maxPost.pop();
                }
                result.add(sinkComponent);
            }

            return result;
        }

        private void exploreAndCollect(Node node, List<Node> collect, Map<Integer, Node> map) {
            node.visited = true;
            for (Node next : node.next) {
                if (!next.visited && map.containsKey(next.value)) {
                    exploreAndCollect(next, collect, map);
                }
            }
            collect.add(node);
        }

        private void cleanVisited(Map<Integer, Node> map) {
            for (Map.Entry<Integer, Node> e : map.entrySet()) {
                e.getValue().visited = false;
            }
        }

        private Map<Integer, Node> invertGraph() {
            Map<Integer, Node> inverse = new HashMap<>();
            for (Map.Entry<Integer, Node> e : map.entrySet()) {
                invert(e.getValue(), inverse);
            }
            return inverse;
        }

        private Node invert(Node node, Map<Integer, Node> inverse) {
            Node node1 = inverse.get(node.value);
            if (node1 != null) {
                return node1;
            }
            Node inverted = new Node(node.value);
            inverse.put(inverted.value, inverted);
            for (Node next : node.next) {
                Node invert = invert(next, inverse);
                inverted.prev.add(invert);
            }

            for (Node prev : node.prev) {
                Node invert = invert(prev, inverse);
                inverted.next.add(invert);
            }
            return inverted;
        }

        private int counter;

        private void DFS(Map<Integer, Node> map) {
            Map<Integer, Node> copy = new HashMap<>(map);
            while (!copy.isEmpty()) {
                DFS(copy.entrySet().iterator().next().getValue(), copy);
            }
        }

        private void DFS(Node node, Map<Integer, Node> map) {
            node.visited = true;

            for (Node next : node.next) {
                if (!next.visited) {
                    DFS(next, map);
                }
            }

            node.post = counter++;
            maxPost.add(node);
            map.remove(node.value);
        }

        private void constructImplicationGraph() {
            createNodes();
            for (Clause clause : clauses) {
                connect(-clause.first, clause.second);
                connect(-clause.second, clause.first);
            }
        }

        private void connect(int a, int b) {
            Node from = map.get(a);
            Node to = map.get(b);
            from.next.add(to);
            to.prev.add(from);
        }

        private void createNodes() {
            for (Clause clause : clauses) {
                createNodes(clause.first);
                createNodes(clause.second);
            }
        }

        private void createNodes(int val) {
            int abs = Math.abs(val);
            Node node = map.get(abs);
            if (node == null) {
                Node n1 = new Node(abs);
                Node n2 = new Node(-abs);
                map.put(abs, n1);
                map.put(-abs, n2);
            }
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        TwoSatisfiability twoSat = new TwoSatisfiability(n, m);
        for (int i = 0; i < m; ++i) {
            twoSat.clauses[i].first = reader.nextInt();
            twoSat.clauses[i].second = reader.nextInt();
        }

        int[] result = new int[n];
        if (twoSat.isSatisfiable(result)) {
            writer.printf("SATISFIABLE\n");
//            for (int i : result) {
//                writer.printf(i + " ");
//            }
            for (int i = 1; i <= n; ++i) {
                if (result[i - 1] == 1) {
                    writer.printf("%d", -i);
                } else {
                    writer.printf("%d", i);
                }
                if (i < n) {
                    writer.printf(" ");
                } else {
                    writer.printf("\n");
                }
            }
        } else {
            writer.printf("UNSATISFIABLE\n");
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}

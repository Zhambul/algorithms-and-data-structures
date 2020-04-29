import java.io.*;
import java.util.*;

public class MaxMatching {
    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new MaxMatching().solve();
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        boolean[][] bipartiteGraph = readData();
        int[] matching = findMatching(bipartiteGraph);
        writeResponse(matching);
        out.close();
    }

    boolean[][] readData() throws IOException {
        int numLeft = in.nextInt();
        int numRight = in.nextInt();
        boolean[][] adjMatrix = new boolean[numLeft][numRight];
        for (int i = 0; i < numLeft; ++i)
            for (int j = 0; j < numRight; ++j)
                adjMatrix[i][j] = (in.nextInt() == 1);
        return adjMatrix;
    }

    private int[] findMatching(boolean[][] bipartiteGraph) {
        FlowGraph graph = new FlowGraph(bipartiteGraph);
        while (true) {
            MinFlow minFlow = graph.minFlow();
            if (minFlow == null) {
                break;
            }
//            System.out.println(minFlow);
            graph.setFlow(minFlow);
            graph = graph.residual();
        }
        return fillMatching(graph, bipartiteGraph.length);
    }

    private int[] fillMatching(FlowGraph graph, int len) {
        int[] matching = new int[len];
        Arrays.fill(matching, -2);

        int s = graph.end;
        List<Edge> edges = graph.graphFrom.get(s);
        if (edges == null) {
            return matching;
        }
        for (Edge edge : edges) {
            List<Edge> VtoU = graph.graphFrom.get(edge.to);
            if (VtoU.size() != 1) {
                throw new RuntimeException("asdasdd");
            }
            Edge e1 = VtoU.get(0);
            int flight = e1.to;
            int crew = e1.from - len;
            matching[flight] = crew;
        }

        return matching;
    }


    private void writeResponse(int[] matching) {
        for (int i = 0; i < matching.length; ++i) {
            if (i > 0) {
                out.print(" ");
            }
            if (matching[i] == -1) {
                out.print("-1");
            } else {
                out.print(matching[i] + 1);
            }
        }
        out.println();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static class FlowGraph {
        private List<Edge> edges;
        private int n;
        private int end;
        private Map<Integer, List<Edge>> graphFrom;
        private Map<Integer, List<Edge>> graphTo;
        private int start;

        public FlowGraph() {
//            this.n = n;
            this.graphFrom = new HashMap<>();
            this.graphTo = new HashMap<>();
//            for (int i = 0; i < n; ++i) {
//                this.graph.put(i, new ArrayList<>());
//                this.graphTo.put(i, new ArrayList<>());
//            }
            this.edges = new ArrayList<>();
        }

        public FlowGraph(boolean[][] all) {
            this();
            int source = -1;
            int sink = -2;
            this.setStart(source);
            this.setEnd(sink);
            for (int i = 0; i < all.length; i++) {
                for (int j = 0, k = all.length; j < all[i].length; j++, k++) {
                    if (all[i][j]) {
                        addEdge(i, k, 1);
                        if (graphFrom.get(k) == null) {
                            addEdge(k, sink, 1);
                        }
                    }
                }
                addEdge(source, i, 1);
            }
        }

        public void addEdge(int from, int to, int capacity) {
            Edge forwardEdge = new Edge(from, to, capacity);
            graphFrom.computeIfAbsent(from, k -> new ArrayList<>());
            graphFrom.get(from).add(forwardEdge);
            graphTo.computeIfAbsent(to, k -> new ArrayList<>());
            graphTo.get(to).add(forwardEdge);
            edges.add(forwardEdge);
        }


        public int size() {
            return graphFrom.size();
        }

        MinFlow minFlow() {
            MinFlow explore = explore(start, new MinFlow());
            if (explore == null) {
                return null;
            }
            Collections.reverse(explore.edges);
            return explore;
        }

        MinFlow explore(int i, MinFlow minFlow) {
            List<Edge> edges = graphFrom.get(i);
            if (edges == null) {
                return null;
            }
            for (Edge edge : edges) {
                if (edge.flow == edge.capacity) {
                    continue;
                }
                if (edge.visited) {
                    continue;
                }
                if (edge.to == end) {
                    return minFlow.addEdge(edge, graphTo);
                }
//                graphTo.get(edge.from).forEach(e1 -> e1.visited = true);
                List<Edge> edges1 = graphTo.get(edge.from);
                if (edges1 != null) {
                    edges1.forEach(e1 -> e1.visited = true);
                }
                MinFlow explored = explore(edge.to, minFlow);
                if (explored != null) {
                    minFlow.addEdge(edge, graphTo);
                    return minFlow;
                }
            }

            return null;
        }

        void setFlow(MinFlow flow) {
            for (Edge edge : flow.edges) {
                edge.setFlow(flow.min);
            }
        }

        FlowGraph residual() {
            FlowGraph residual = new FlowGraph();
            for (Edge edge : edges) {
                if (edge.flow != 0) {
                    residual.addEdge(edge.to, edge.from, edge.flow);
                }
                if (edge.getLeft() != 0) {
                    residual.addEdge(edge.from, edge.to, edge.getLeft());
                }
            }
            residual.setEnd(this.end);
            residual.setStart(this.start);
//            sortEdges(residual);
            return residual;
        }

//        void sortEdges(FlowGraph graph) {
//            graph.graph.forEach((k, v) -> {
////                v.sort(Comparator.comparingInt(Edge::getLeft).reversed());
//                v.sort(Comparator.comparingInt(e -> graph.graphTo.get(e.to).size()));
//            });
//        }

        public void setEnd(int end) {
            this.end = end;
        }

        public void setStart(int from) {
            this.start = from;
        }
    }

    static class Edge {
        int from, to, capacity, flow;
        boolean visited;

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        public int getLeft() {
            return capacity - flow;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + (from) +
                    ", to=" + (to) +
                    '}';
        }

        public void setFlow(int flow) {
            this.flow = flow;
        }
    }

    static class MinFlow {
        private int min = Integer.MAX_VALUE;
        private List<Edge> edges;

        public MinFlow(MinFlow flow) {
            this.min = flow.min;
            this.edges = new ArrayList<>(flow.edges);
        }

        public MinFlow() {
            edges = new ArrayList<>();
        }


        public MinFlow addEdge(Edge e, Map<Integer, List<Edge>> graph) {
            this.edges.add(e);
            tryMin(e.getLeft());
            e.visited = true;
            List<Edge> edges = graph.get(e.from);
            if (edges != null) {
                edges.forEach(e1 -> e1.visited = true);
            }
            return this;
        }

        public void tryMin(int min) {
            this.min = Math.min(this.min, min);
        }

        @Override
        public String toString() {
            return "MinFlow{" +
                    "min=" + min +
                    ", edges=" + edges +
                    '}';
        }
    }

}

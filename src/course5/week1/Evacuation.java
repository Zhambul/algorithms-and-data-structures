import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Evacuation {
    private static FastScanner in;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();

        FlowGraph graph = readGraph();
        System.out.println(maxFlow(graph));
    }

    private static int maxFlow(FlowGraph graph) {
        int flow = 0;
        graph.sortEdges(graph);
        while (true) {
            MinFlow minFlow = graph.minFlow();
            if (minFlow == null) {
                break;
            }
            flow += minFlow.min;
            graph.setFlow(minFlow);
            graph = graph.residual(minFlow);
        }

        return flow;
    }

    static FlowGraph readGraph() throws IOException {
        int vertex_count = in.nextInt();
        int edge_count = in.nextInt();
        FlowGraph graph = new FlowGraph(vertex_count);

        for (int i = 0; i < edge_count; ++i) {
            int from = in.nextInt() - 1;
            int to = in.nextInt() - 1;
            int capacity = in.nextInt();
            graph.addEdge(from, to, capacity);
            if (i == 0) {
                graph.setStart(from);
            }
        }

        graph.setEnd(vertex_count - 1);
        return graph;
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
                    "from=" + (from + 1) +
                    ", to=" + (to + 1) +
                    ", capacity=" + capacity +
                    ", flow=" + flow +
                    '}';
        }

        public void setFlow(int flow) {
            this.flow = flow;
        }
    }

    /* This class implements a bit unusual scheme to store the graph edges, in order
     * to retrieve the backward edge for a given edge quickly. */
    static class FlowGraph {
        private List<Edge> edges;
        private int n;
        private int end;
        private Map<Integer, List<Edge>> graph;
        private Map<Integer, List<Edge>> graphTo;
        private int start;

        public FlowGraph(int n) {
            this.n = n;
            this.graph = new HashMap<>();
            this.graphTo = new HashMap<>();
            for (int i = 0; i < n; ++i) {
                this.graph.put(i, new ArrayList<>());
                this.graphTo.put(i, new ArrayList<>());
            }
            this.edges = new ArrayList<>();
        }

        public void addEdge(int from, int to, int capacity) {
            addEdge(from, to, capacity, 0);
        }

        public void addEdge(int from, int to, int capacity, int flow) {
            Edge forwardEdge = new Edge(from, to, capacity);
            forwardEdge.flow = flow;
            graph.get(from).add(forwardEdge);
            graphTo.get(to).add(forwardEdge);
            edges.add(forwardEdge);
        }

        public int size() {
            return graph.size();
        }

        MinFlow minFlow() {
            return explore(start, new MinFlow());
        }

        MinFlow explore(int i, MinFlow minFlow) {
            List<Edge> edges = graph.get(i);
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
                graphTo.get(edge.from).forEach(e1 -> e1.visited = true);

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

        FlowGraph residual(MinFlow minFlow) {
            FlowGraph residual = new FlowGraph(n);
            for (Edge edge : edges) {
                if (edge.flow != 0) {
                    residual.addEdge(edge.to, edge.from, edge.flow, 0);
                }
                if (edge.getLeft() != 0) {
                    residual.addEdge(edge.from, edge.to, edge.getLeft(), 0);
                }
            }
            residual.setEnd(this.end);
            residual.setStart(this.start);
            sortEdges(residual);
            return residual;
        }

        void sortEdges(FlowGraph graph) {
            graph.graph.forEach((k, v) -> {
                v.sort(Comparator.comparingInt(Edge::getLeft).reversed());
            });
        }
        public void setEnd(int end) {
            this.end = end;
        }

        public void setStart(int from) {
            this.start = 0;
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
            graph.get(e.from).forEach(e1 -> e1.visited = true);
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
}

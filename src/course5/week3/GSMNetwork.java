import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GSMNetwork {
    private final InputReader reader;
    private final OutputWriter writer;

    public GSMNetwork(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new GSMNetwork(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertGSMNetworkProblemToSat {
        int numVertices;
        Edge[] edges;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int[][] A;

        ConvertGSMNetworkProblemToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
            A = new int[n][3];
        }

        void printEquisatisfiableSatFormula() {
            List<List<Integer>> res = new ArrayList<>();
            int var = 1;
            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                int k = e.getKey();
                //R
                int c1 = var++;
                //G
                int c2 = var++;
                //B
                int c3 = var++;
                //save colors
                A[k][0] = c1;
                A[k][1] = c2;
                A[k][2] = c3;

                List<Integer> a = new ArrayList<>();

                // (R OR G OR B)
                a.add(c1);
                a.add(c2);
                a.add(c3);
                res.add(a);
                // AND
                // R NAND G
                res.add(nand(c1, c2));
//                // AND
//                // R NAND B
                res.add(nand(c1, c3));
//                // AND
//                // G NAND B
                res.add(nand(c2, c3));
            }

            for (Edge edge : edges) {
                res.add(nand(A[edge.from][0], A[edge.to][0]));
                res.add(nand(A[edge.from][1], A[edge.to][1]));
                res.add(nand(A[edge.from][2], A[edge.to][2]));
            }

            printResult(res, var);

        }
    }

    private void printResult(List<List<Integer>> res, int var) {
        System.out.print(res.size() + " " + (var - 1) + "\n");
        for (List<Integer> line : res) {
            System.out.println(line.stream().map(String::valueOf).collect(Collectors.joining(" ")) + " 0");
        }
    }

    private List<Integer> nand(int a, int b) {
        List<Integer> c = new ArrayList<>();
        c.add(-a);
        c.add(-b);
        return c;
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertGSMNetworkProblemToSat converter = new ConvertGSMNetworkProblemToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt() - 1;
            converter.edges[i].to = reader.nextInt() - 1;
            converter.map.computeIfAbsent(converter.edges[i].from, k -> new HashSet<>());
            converter.map.computeIfAbsent(converter.edges[i].to, k -> new HashSet<>());
            converter.map.get(converter.edges[i].from).add(converter.edges[i].to);
            converter.map.get(converter.edges[i].to).add(converter.edges[i].from);
        }

        converter.printEquisatisfiableSatFormula();
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

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CleaningApartment {
    private final InputReader reader;
    private final OutputWriter writer;

    public CleaningApartment(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new CleaningApartment(reader, writer).run();
        writer.writer.flush();
    }

    class Edge {
        int from;
        int to;
    }

    class ConvertHampathToSat {
        int numVertices;
        Edge[] edges;
        int[][] A;
        Set<Integer> allVertices = new HashSet<>();
        Map<Integer, Set<Integer>> map = new HashMap<>();

        ConvertHampathToSat(int n, int m) {
            numVertices = n;
            edges = new Edge[m];
            for (int i = 0; i < m; ++i) {
                edges[i] = new Edge();
            }
            A = new int[n][n];
        }

        void printEquisatisfiableSatFormula() {
            List<List<Integer>> res = new ArrayList<>();
            int var = 1;

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                int k = e.getKey();
                for (int i = 0; i < numVertices; i++) {
                    A[k][i] = var++;
                }
            }

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                int k = e.getKey();
                List<Integer> shouldBeInThePath = new ArrayList<>();
                res.add(shouldBeInThePath);
                for (int i = 0; i < numVertices; i++) {
                    shouldBeInThePath.add(A[k][i]);
                }
            }

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                List<List<Integer>> columns = new ArrayList<>();

                for (int i = 0; i < numVertices; i++) {
                    List<Integer> column = new ArrayList<>();
                    for (int j = 0; j < numVertices; j++) {
                        column.add(A[j][i]);
                    }
                    columns.add(column);
                }
                res.addAll(columns);
            }

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                for (int i = 0; i < numVertices; i++) {
                    int[][] pairs = combinationsOfTwo(A[i]);
                    for (int[] pair : pairs) {
                        res.add(nand(pair[0], pair[1]));
                    }
                }
            }

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                List<int[]> columns = new ArrayList<>();

                for (int i = 0; i < numVertices; i++) {
                    int[] column = new int[numVertices];
                    for (int j = 0; j < numVertices; j++) {
                        column[j] = A[j][i];
                    }
                    columns.add(column);
                }

                for (int[] column : columns) {
                    int[][] pairs = combinationsOfTwo(column);
                    for (int[] pair : pairs) {
                        res.add(nand(pair[0], pair[1]));
                    }
                }
            }

            for (Map.Entry<Integer, Set<Integer>> e : map.entrySet()) {
                Set<Integer> diff = new HashSet<>(allVertices);
                diff.removeAll(e.getValue());
                Integer k1 = e.getKey();
                for (Integer k2 : diff) {
                    if (k1.equals(k2)) {
                        continue;
                    }
                    for (int i = 0; i < numVertices - 1; i++) {
                        res.add(nand(A[k1][i], A[k2][i + 1]));
                    }
                }
            }
            printResult(res, var);
        }
    }

    private static int[][] combinationsOfTwo(int[] a) {
        long asd1 = fact(a.length);
        long asd2 = fact(a.length - 2);
        int combSize = (int) (fact(a.length) / (2 * fact(a.length - 2)));
        int res[][] = new int[combSize][2];
        combinationsOfTwo(a, 0, 0, res);
        return res;
    }

    private static void combinationsOfTwo(int[] a, int i, int k, int[][] res) {
        if (i == a.length) {
            return;
        }
        int s = a[i];
        for (int j = i + 1; j < a.length; j++) {
            int[] combinationsOfS = new int[2];
            combinationsOfS[0] = s;
            combinationsOfS[1] = a[j];
            res[k] = combinationsOfS;
            k++;
        }
        i++;
        combinationsOfTwo(a, i, k, res);
    }

    static List<Long> Fa = new ArrayList<>();
    static {
        Fa.add(0, 0L);
        Fa.add(0, 0L);
        Fa.add(1, 1L);
        Fa.add(2, 2L);
    }

    private static long fact(long n) {
        if (Fa.size() > n && Fa.get((int) n) != null) {
            return Fa.get((int) n);
        }
        Fa.set((int) n, n * fact(n - 1));
        return Fa.get((int) n);
    }

//    private static List<int[]> _combinationsOfTwo(List<Integer> a) {
//        List<int[]> res = new ArrayList<>();
//        int s = a.remove(0);
//        for (int i = 0; i < a.size(); i++) {
//            int[] combinationsOfS = new int[2];
//            combinationsOfS[0] = s;
//            combinationsOfS[1] = a.get(i);
//            res.add(combinationsOfS);
//        }
//        if (a.isEmpty()) {
//            return res;
//        }
//        res.addAll(_combinationsOfTwo(a));
//        return res;
//    }

    private List<Integer> nand(int a, int b) {
        List<Integer> c = new ArrayList<>(2);
        c.add(-a);
        c.add(-b);
        return c;
    }

    private void printResult(List<List<Integer>> res, int var) {
        System.out.print(res.size() + " " + (var - 1) + "\n");
        for (List<Integer> line : res) {
            System.out.println(line.stream().map(String::valueOf).collect(Collectors.joining(" ")) + " 0");
        }
    }

    public void run() {
        int n = reader.nextInt();
        int m = reader.nextInt();

        ConvertHampathToSat converter = new ConvertHampathToSat(n, m);
        for (int i = 0; i < m; ++i) {
            converter.edges[i].from = reader.nextInt() - 1;
            converter.edges[i].to = reader.nextInt() - 1;

            converter.map.computeIfAbsent(converter.edges[i].from, k -> new HashSet<>());
            converter.map.computeIfAbsent(converter.edges[i].to, k -> new HashSet<>());
            converter.map.get(converter.edges[i].from).add(converter.edges[i].to);
            converter.map.get(converter.edges[i].to).add(converter.edges[i].from);
            converter.allVertices.add(converter.edges[i].from);
            converter.allVertices.add(converter.edges[i].to);
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

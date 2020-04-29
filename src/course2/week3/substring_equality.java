import java.util.*;
import java.io.*;

public class substring_equality {

    private static int p1 = 1000000007;
    private static int p2 = 1000000009;
    private static int x1 = 31;
    private static int x2 = 127;

    public class Solver {
        protected String s;

        public Solver(String s) {
            this.s = s;
        }

        public boolean ask(int a, int b, int l) {
            return s.substring(a, a + l).equals(s.substring(b, b + l));
        }
    }

    public class QuickSolver extends Solver {
        int[] H1;
        int[] H2;

        public QuickSolver(String s) {
            super(s);
            H1 = new int[s.length()];
            H2 = new int[s.length()];
            precompute();
        }

        private void precompute() {
            H1[0] = s.charAt(0) * x1;
            for (int i = 1; i < s.length(); i++) {
                H1[i] = (H1[i - 1] + s.charAt(i) * x1) % p1;
                H2[i] = (H2[i - 1] + s.charAt(i) * x2) % p2;
            }
        }

        @Override
        public boolean ask(int a, int b, int l) {
            int h1 = H1[a + l - 1];
            int h2 = H1[b + l - 1];
            if (a > 0) {
                h1 -= H1[a - 1];
            }
            if (b > 0) {
                h2 -= H1[b - 1];
            }

            int h3 = H2[a + l - 1];
            int h4 = H2[b + l - 1];
            if (a > 0) {
                h3 -= H2[a - 1];
            }
            if (b > 0) {
                h4 -= H2[b - 1];
            }
            return h1 == h2 && h3 == h4;
        }
    }

    public void run() throws IOException {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        String s = in.next();
        int q = in.nextInt();
        Solver solver = new QuickSolver(s);
        for (int i = 0; i < q; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int l = in.nextInt();
            out.println(solver.ask(a, b, l) ? "Yes" : "No");
        }
        out.close();
    }

    static public void main(String[] args) throws IOException {
        new substring_equality().run();
    }

    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] a;
    private List<Swap> swaps;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
            out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
        swaps = new ArrayList<>();
        for (int i = (a.length / 2); i >= 0; i--) {
            siftDown(i);
        }
    }

    //min heap
    private void siftDown(int i) {
        int value = a[i];
        int leftI = i * 2 + 1;
        int rightI = i * 2 + 2;

        Integer left = null;
        Integer right = null;

        if (leftI < a.length) {
            left = a[leftI];
        }
        if (rightI < a.length) {
            right = a[rightI];
        }

        if (left != null && right != null) {
            if (left > value && right > value) {
                return;
            }
            if (left < right) {
                swap(i, leftI);
                siftDown(leftI);
            } else {
                swap(i, rightI);
                siftDown(rightI);
            }
        } else if (left != null) {
            if (left > value) {
                return;
            }
            swap(i, leftI);
            siftDown(leftI);
        } else if (right != null){
            if (right > value) {
                return;
            }
            swap(i, rightI);
            siftDown(right);
        }
    }

    private void swap(int i, int j) {
        swaps.add(new Swap(i, j));
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
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

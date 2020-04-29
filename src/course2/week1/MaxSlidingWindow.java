package course2.week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class MaxSlidingWindow {
    static class FastScanner {
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

    static class MaxStack<T extends Comparable<T>> extends Stack<T> {
        private Stack<T> maxs = new Stack<>();
        private final int windowSize;

        public MaxStack(int windowSize) {
            this.windowSize = windowSize;
        }

        @Override
        public T push(T item) {
            T prevMax = null;
            if (maxs.size() > 0) {
                prevMax = maxs.peek();
            }
            T max;
            if (prevMax == null) {
                max = item;
            } else {
                int i = prevMax.compareTo(item);
                if (i < 0) {
                    max = item;
                } else {
                    max = prevMax;
                }
            }
            maxs.push(max);
            return super.push(item);
        }

        @Override
        public synchronized T pop() {
            maxs.pop();
            return super.pop();
        }

        public T max() {
            return maxs.peek();
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        MaxStack<Integer> stack = new MaxStack<>(m);
        for (int i = 0; i < n; i++) {
            stack.push(a[i]);
        }

        for (int i = 0; i < n - m + 1; i++) {

        }
    }
}

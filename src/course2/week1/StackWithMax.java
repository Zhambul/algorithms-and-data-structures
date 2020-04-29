import java.util.*;
import java.io.*;

public class StackWithMax {
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

    class MaxStack<T extends Comparable<T>> extends Stack<T> {
        private Stack<T> maxs = new Stack<>();

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

    public void solve() throws IOException {
        FastScanner scanner = new FastScanner();
        int queries = scanner.nextInt();
        MaxStack<Integer> stack = new MaxStack<>();

        for (int qi = 0; qi < queries; ++qi) {
            String operation = scanner.next();
            if ("push".equals(operation)) {
                int value = scanner.nextInt();
                stack.push(value);
            } else if ("pop".equals(operation)) {
                stack.pop();
            } else if ("max".equals(operation)) {
                System.out.println(stack.max());
            }
        }
    }

    static public void main(String[] args) throws IOException {
        new StackWithMax().solve();
    }
}

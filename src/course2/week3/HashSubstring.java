import java.io.*;
import java.util.*;

public class HashSubstring {

    private static FastScanner in;
    private static PrintWriter out;
    private static int prime = 1000000007;
    private static int multiplier = 1;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur : ans) {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        List<Integer> occurrences = new ArrayList<>();
        long pHash = hash(input.pattern);
        long[] H = precompute(input.text, input.pattern.length());

        for (int i = 0; i < input.text.length() - input.pattern.length() + 1; i++) {
            if (pHash != H[i]) {
                continue;
            }
            if (input.pattern.equals(input.text.substring(i, i + input.pattern.length()))) {
                occurrences.add(i);
            }
        }
        return occurrences;
    }

    private static long[] precompute(String input, int patternSize) {
        long[] H = new long[input.length() - patternSize + 1];
        String S = input.substring(input.length() - patternSize);
        H[input.length() - patternSize] = hash(S);
        long y = 1;
        for (int i = 0; i < patternSize; i++) {
            y = (y * multiplier) % prime;
        }
        for (int i = input.length() - patternSize - 1; i >= 0; i--) {
            long c = (multiplier * H[i + 1] + input.charAt(i) - y * input.charAt(i + patternSize)) % prime;
            H[i] = c;
        }
        return H;
    }

    private static long hash(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return hash;
    }

    static class Data {
        String pattern;
        String text;

        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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


import java.util.*;
import java.io.*;

public class SuffixArrayLong {
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

    public class Suffix implements Comparable {
        String suffix;
        int start;

        Suffix(String suffix, int start) {
            this.suffix = suffix;
            this.start = start;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix) o;
            return suffix.compareTo(other.suffix);
        }
    }

    // Build suffix array of the string text and
    // return an int[] result of the same length as the text
    // such that the value result[i] is the index (0-based)
    // in text where the i-th lexicographically smallest
    // suffix of text starts.
    public int[] computeSuffixArray(String text) {
        int[] result = new int[text.length()];
        // write your code here
        text = "AACGATAGCGGTAGA$";
//        System.out.println(Arrays.toString(sortCharacters(text)));
        System.out.println(Arrays.toString(classes(text, sortCharacters(text))));
        return result;
    }

    static public int[] classes(String text, int[] a) {
        int[] classes = new int[a.length];
        int counter = 0;
        for (int i = 1; i < a.length; i++) {
            char c = text.charAt(a[i]);
            char v = text.charAt(a[i - 1]);
            if (c == v) {
                classes[a[i]] = classes[a[i - 1]];
            } else {
                classes[a[i]] = ++counter;
            }
        }
        return classes;
    }

    static public int[] sortCharacters(String text) {
        int[] order = new int[text.length()];
        int[] count = new int[27];

        for (char c : text.toCharArray()) {
            count[toInt(c)]++;
        }
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i - 1];
        }
        for (int i = text.length() - 1; i >= 0; i--) {
            int c = toInt(text.charAt(i));
            count[c] = count[c] - 1;
            order[count[c]] = i;
        }
        return order;
    }

    static int toInt(char c) {
        int res = c - 65;
        if (res == -29) {
            return 0;
        }
        return res + 1;
    }

    static public void main(String[] args) throws IOException {
        new SuffixArrayLong().run();
    }

    public void print(int[] x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int[] suffix_array = computeSuffixArray(text);
        print(suffix_array);
    }
}

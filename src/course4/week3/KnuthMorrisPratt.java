import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KnuthMorrisPratt {
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

    // Find all the occurrences of the pattern in the text and return
    // a list of all positions in the text (starting from 0) where
    // the pattern starts in the text.
    public List<Integer> findPattern(String pattern, String text) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        // Implement this function yourself
        System.out.println(Arrays.toString(prefix("ACATACATACACA")));
//        String S = pattern + "$" + text;
//        int pLenth = pattern.length();
//        int twopLenth = pLenth * 2;
//        int[] prefix = prefix(S);
//        for (int i = pLenth + 1; i < S.length(); i++) {
//            if (prefix[i] == pLenth) {
//                result.add(i - twopLenth);
//            }
//        }
        return result;
    }

    static int[] prefix(String text) {
        int[] s = new int[text.length()];
        s[0] = 0;
        int border = 0;
        for (int i = 1; i < text.length(); i++) {
            while (border > 0 && text.charAt(i) != text.charAt(border)) {
                border = s[border - 1];
            }
            if (text.charAt(i) == text.charAt(border)) {
                border++;
            } else {
                border = 0;
            }
            s[i] = border;
        }
        return s;
    }

    static public void main(String[] args) throws IOException {
        new KnuthMorrisPratt().run();
    }

    public void print(List<Integer> x) {
        for (int a : x) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String pattern = scanner.next();
        String text = scanner.next();
        List<Integer> positions = findPattern(pattern, text);
        print(positions);
    }
}

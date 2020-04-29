import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiFunction;

public class InverseBWT {
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

    String inverseBWT(String lastRow) {
        StringBuilder result = new StringBuilder();

        // write your code here
        char[] a = lastRow.toCharArray();
        Arrays.sort(a);
        String firstRow = new String(a);

        String[] first = count(firstRow);
        String[] last = count(lastRow);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < lastRow.length(); i++) {
            final int j = i;
            map.compute(first[i], (s, s2) -> last[j]);
        }

        result.append(firstRow.charAt(0));
        String b = String.valueOf(lastRow.charAt(0)) + 1;

        for (int i = 0; i < lastRow.length() - 1; i++) {
            result.append(b.charAt(0));
            b = map.get(b);
        }

        return result.reverse().toString();
    }

    private String[] count(String firstRow) {
        String[] res = new String[firstRow.length()];
        Map<Character, Integer> count = new HashMap<>();

        for (int i = 0; i < firstRow.length(); i++) {
            count.compute(firstRow.charAt(i), (character, counter) -> {
                if (counter == null) {
                    return 1;
                }
                return counter + 1;
            });
            res[i] = firstRow.charAt(i) + String.valueOf(count.get(firstRow.charAt(i)));
        }
        return res;
    }

    static public void main(String[] args) throws IOException {
        new InverseBWT().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String bwt = scanner.next();
        System.out.println(inverseBWT(bwt));
    }
}

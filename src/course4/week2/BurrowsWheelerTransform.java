import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BurrowsWheelerTransform {
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

    String BWT(String text) {
        String[] matrix = new String[text.length()];
        matrix[0] = text;
        StringBuilder result = new StringBuilder();

        for (int i = 1; i < text.length(); i++) {
            matrix[i] = shiftRight(matrix[i - 1]);
        }
        Arrays.sort(matrix);

        for (int i = 0; i < matrix.length; i++) {
            result.append(matrix[i].charAt(text.length() - 1));
        }

        return result.toString();
    }

    String shiftRight(String text) {
        return text.substring(1) + text.charAt(0);
    }

    static public void main(String[] args) throws IOException {
        new BurrowsWheelerTransform().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        System.out.println(BWT(text));
    }
}

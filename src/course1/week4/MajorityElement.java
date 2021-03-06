import java.util.*;
import java.io.*;
import java.util.function.BiFunction;

public class MajorityElement {
    private static int getMajorityElement(int[] a, int left, int right) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            count.compute(a[i], (key, c) -> {
                if (c == null) {
                    return 1;
                }
                return c + 1;
            });
        }
        Map.Entry<Integer, Integer> c = count.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElseThrow(IllegalStateException::new);
        if (c.getValue() > a.length / 2) {
            return 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        if (getMajorityElement(a, 0, a.length) != -1) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlacingParentheses {
    static List<Long> params;
    static List<Character> ops;
    static long[][] m;
    static long[][] M;

    private static long getMaximValue(String exp) {
        params = Arrays.stream(exp.split("\\+|-|\\*")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        ops = Arrays.stream(exp.split("[0-9]+")).filter(it -> !it.isEmpty()).map(s -> s.toCharArray()[0]).collect(Collectors.toList());
        m = new long[params.size()][params.size()];
        M = new long[params.size()][params.size()];
        for (int i = 0; i < params.size(); i++) {
            m[i][i] = params.get(i);
            M[i][i] = params.get(i);
        }

        for (int s = 0; s < params.size(); s++) {
            for (int i = 0; i < params.size() - s; i++) {
                int j = i + s;
                if (i == j) {
                    continue;
                }
                long[] ints = minAndMax(i, j);
                m[i][j] = ints[0];
                M[i][j] = ints[1];
            }
        }
//        debugPrint(m);
        //write your code here
        return M[0][params.size() - 1];
    }

    private static void debugPrint(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            System.out.println(Arrays.toString(m[i]));
        }
    }

    private static long[] minAndMax(long i, long j) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for (long k = i; k < j; k++) {
            char op = ops.get((int)k);
            long a = eval(op, M[(int)i][(int)k], M[(int)k + 1][(int)j]);
            long b = eval(op, M[(int)i][(int)k], m[(int)k + 1][(int)j]);
            long c = eval(op, m[(int)i][(int)k], M[(int)k + 1][(int)j]);
            long d = eval(op, m[(int)i][(int)k], m[(int)k + 1][(int)j]);
            min = Math.min(min, Math.min(a, Math.min(b, Math.min(c, d))));
            max = Math.max(max, Math.max(a, Math.max(b, Math.max(c, d))));
        }
        long[] res = new long[2];
        res[0] = min;
        res[1] = max;
        return res;
    }

    private static long eval(char op, long a, long b) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            assert false;
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.next();
        System.out.println(getMaximValue(exp));
    }
}


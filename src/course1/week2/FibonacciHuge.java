package course1.week2;

import java.util.*;

public class FibonacciHuge {
    static List<Long> fibTable = new ArrayList<>();

    static {
        fibTable.add(0L);
        fibTable.add(1L);
    }

    private static long getFibonacciHugeNaive(long n, long m) {
        long[] pisanoPeriod = getPisanoPeriod(n, m);
        int a = (int) (n % pisanoPeriod.length);
        return pisanoPeriod[a];
    }

    private static long calc_fib(int n) {
        if (n <= 1) {
            return n;
        }
        long[] fibTable = new long[n + 1];
        fibTable[0] = 0;
        fibTable[1] = 1;

        for (int i = 2; i < n + 1; i++) {
            long a = fibTable[i - 1] + fibTable[i - 2];
            fibTable[i] = a;
        }
        return fibTable[n];
    }

    private static long[] getPisanoPeriod(long n, long m) {
        if (n == 1)
            return new long[]{0, 1};

        if (n == 0)
            return new long[]{0};

        List<Long> fibTable = new ArrayList<>();

        fibTable.add(0L);
        fibTable.add(1L);

        int i = 2;
        while (true) {
            fibTable.add((fibTable.get(i - 1) + fibTable.get(i - 2)) % m);
            if (fibTable.get(fibTable.size() - 1) == 1 && fibTable.get(fibTable.size() - 2) == 0) {
                fibTable.remove(fibTable.size() - 1);
                fibTable.remove(fibTable.size() - 1);
                return fibTable.stream().mapToLong(it -> it).toArray();
            }
            i++;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        System.out.println(getFibonacciHugeNaive(n, m));
    }
}


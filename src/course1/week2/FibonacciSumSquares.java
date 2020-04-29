package course1.week2;

import java.util.*;

public class FibonacciSumSquares {
    private static long getFibonacciSumSquaresNaive(long n) {
        if (n == 0) {
            return 0;
        }
        long a = getFibonacciLastDigit(n);
        long b = getFibonacciLastDigit(n - 1);
        long c = (a+b) % 10;

        return (c * a) % 10;
    }

    private static long getFibonacciLastDigit(long n) {
        long[] pisanoPeriod = getPisanoPeriod(10);
        int a = (int) (n % pisanoPeriod.length);
        return pisanoPeriod[a];
    }

    private static long[] getPisanoPeriod(long m) {
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
        long s = getFibonacciSumSquaresNaive(n);
        System.out.println(s);
    }
}


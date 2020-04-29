package course1.week2;

import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        long a = getFibonacciSum(from - 1);
        long b = getFibonacciSum(to);
        if (b < a) {
            b += 10;
        }
        return b - a;
    }

    private static long getFibonacciSum(long n) {
        long[] pisanoPeriod = getPisanoPeriod(10);
        long sum = 0;
        for (int i = 0; i < pisanoPeriod.length; i++) {
            sum += pisanoPeriod[i];
        }
        long allSum;
        long a = n / pisanoPeriod.length;
        long a2 = n % pisanoPeriod.length;
        allSum = a * sum;

        for (int i = 0; i < a2 + 1; i++) {
            allSum += pisanoPeriod[i];
        }
        return allSum % 10;
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
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumNaive(from, to));
    }
}


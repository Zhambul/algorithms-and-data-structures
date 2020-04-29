package course1.week2;

import java.util.*;

public class FibonacciLastDigit {
    private static long getFibonacciLastDigit(int n) {
        long[] pisanoPeriod = getPisanoPeriod(10);
        int a = n % pisanoPeriod.length;
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
        int n = scanner.nextInt();
        long c = getFibonacciLastDigit(n);
        System.out.println(c);
    }
}


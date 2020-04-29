package course1.week2;

import java.util.Scanner;

public class Fibonacci {
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
        return fibTable[n ];
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        System.out.println(calc_fib(n));
    }
}

package course1.week2;

import java.util.*;

public class LCM {
    private static long lcm_naive(int a, int b) {
        long max = Math.max(a, b);
        long n = 1;
        while (true) {
            long c = max * n;
            if (c % a == 0 && c % b == 0) {
                return c;
            }
            n++;
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(lcm_naive(a, b));
    }
}

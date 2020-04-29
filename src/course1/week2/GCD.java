package course1.week2;

import java.util.*;

public class GCD {
    private static int gcd_naive(int a, int b) {
        return euclid(a, b);
    }

    private static int euclid(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        if (a > b) {
            return euclid(a % b, b);
        }
        return euclid(a, b % a);
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.println(gcd_naive(a, b));
    }
}

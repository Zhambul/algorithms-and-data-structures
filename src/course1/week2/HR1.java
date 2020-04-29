package course1.week2;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HR1 {
    public static void main(String[] args) {
        System.out.println(jumpingOnClouds( new int[] {0, 0 ,0, 0, 1, 0}));
    }

    // Complete the sockMerchant function below.
    static int sockMerchant(int n, int[] c) {
        Set<Integer> colors = new HashSet<>();
        int pairs = 0;

        for (int i = 0; i < n; i++) {
            if (!colors.contains(c[i])) {
                colors.add(c[i]);
            } else {
                pairs++;
                colors.remove(c[i]);
            }
        }
        return pairs;
    }

    static int countingValleys(int n, String s) {
        int lvl = 0;
        int valleys = 0;
        for (char c : s.toCharArray()) {
            if (c == 'U') ++lvl;
            if (c == 'D') --lvl;

            if (lvl == 0 && c == 'U') {
                valleys++;
            }
        }
        return valleys;
    }

    // Complete the jumpingOnClouds function below.
    static int jumpingOnClouds(int[] c) {
        int currentCloud = 0;
        int jumps = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextInt() + scanner.nextInt());


        while (currentCloud <= c.length) {
            if (currentCloud + 2 < c.length && c[currentCloud + 2] == 0) {
                currentCloud = currentCloud + 2;
                System.out.println("jump");
                jumps++;
            }
            else if (currentCloud + 1 < c.length && c[currentCloud + 1] == 0) {
                currentCloud = currentCloud + 1;
                System.out.println("jump");
                jumps++;
            } else break;
        }
        return jumps;
    }
}

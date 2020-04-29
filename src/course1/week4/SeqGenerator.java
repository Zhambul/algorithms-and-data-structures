package course1.week4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SeqGenerator {

    public static void main(String[] args) {
        int a = 100;
        int[] n = new int[a];
        Random random = new Random();
        for (int i = 0; i < a; i++) {
            n[i] = random.nextInt( 10);
        }

        System.out.println(a);
        System.out.println(Arrays.stream(n).boxed().map(String::valueOf).collect(Collectors.joining(" ")));

        Scanner scanner = new Scanner(System.in);

        int[] nS = new int[a];
        for (int i = 0; i < a; i++) {
            nS[i] = scanner.nextInt();
        }

        Arrays.sort(n);
        System.out.println(Arrays.stream(n).boxed().map(String::valueOf).collect(Collectors.joining(" ")));

        boolean bad = false;
        for (int i = 0; i < a; i++) {
            if (n[i] != nS[i]) {
                bad = true;
                System.out.println("BAD! " + i);
            }
        }
        if (!bad) {
            System.out.println("GOOD");
        }
    }
}


import java.util.Scanner;

public class Change {
    private static int getChange(int m) {
        int coins = 0;
        int left = m;
        while (left != 0) {
            if (left >= 10) {
                left -= 10;
                coins++;
            } else if (left >= 5) {
                left -= 5;
                coins++;
            } else if (left >= 1) {
                left-= 1;
                coins++;
            }
        }
        //write your code here
        return coins;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}


import java.util.Scanner;

public class ChangeDP {

    static int[] D;

    private static int getChange(int m) {
        D = new int[m + 1];
        for (int i = 0; i < m + 1; i++) {
            D[i] = -1;
        }

        for (int i = 0; i < m + 1; i++) {
            D[i] = _getChange(i);
        }
        return D[m];
    }

    private static int _getChange(int m) {
        if (m == 0) {
            return 0;
        }
        if (m < 0) {
            return Integer.MAX_VALUE;
        }
        if (D[m] != -1) {
            return D[m];
        }

        int b = _getChange(m - 4);
        int a = _getChange(m - 3);
        int c = _getChange(m - 1);
        return Math.min(a, Math.min(b, c)) + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        System.out.println(getChange(m));

    }
}


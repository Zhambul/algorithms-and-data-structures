import java.util.*;

public class LCS2 {
    static int[] A;
    static int[] B;
    static int[][] D;

    private static int lcs2(int[] a, int[] b) {
        //Write your code here
        A = a;
        B = b;
        D = new int[a.length + 1][b.length + 1];

        _editDistance(a.length, b.length);
        debugPrint(a, b);
        List<Integer> sub = reverse(a, b);
        System.out.println(sub);
        return D[a.length][b.length];
    }

    private static List<Integer> reverse(int[] a, int[] b) {
        List<Integer> sub = new ArrayList<>();
        int g = D[a.length][b.length];
        for (int i = D.length - 1; i >= 1; i--) {
            int point = D[i][b.length];
            if (point == g) {
                sub.add(a[i - 1]);
                g--;
            }
        }
        Collections.reverse(sub);
        return sub;
    }

    private static void debugPrint(int[] a, int[] b) {
        System.out.println("     " + Arrays.toString(b));
        for (int i = 1; i < a.length + 1; i++) {
            System.out.println(a[i - 1] + " " + Arrays.toString(D[i]));
        }
    }

    private static int _editDistance(int i, int j) {
        if (i == 0) {
            D[i][j] = 0;
            return 0;
        }
        if (j == 0) {
            D[i][j] = 0;
            return 0;
        }
        if (D[i][j] != 0) {
            return D[i][j];
        }
        int ins = _editDistance(i - 1, j);
        int del = _editDistance(i, j - 1);
        int sub = _editDistance(i - 1, j - 1) + diff(A[i - 1], B[j - 1]);
        int max = Math.max(ins, Math.max(del, sub));
        D[i][j] = max;
        return max;
    }

    private static int diff(int a, int b) {
        if (a == b) {
            return 1;
        }
        return Integer.MIN_VALUE;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        int m = scanner.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = scanner.nextInt();
        }

        System.out.println(lcs2(a, b));
    }
}


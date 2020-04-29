import java.util.*;

public class LCS3 {

    static int[] A;
    static int[] B;
    static int[] C;

    static int[][] D;
    static int[][][] D2;

    private static int lcs3(int[] a, int[] b, int[] c) {
        //Write your code here
        A = a;
        B = b;
        C = c;
        D2 = new int[A.length + 1][B.length + 1][C.length + 1];
        for (int i = 0; i < D2.length; i++) {
            for (int j = 0; j < D2[i].length; j++) {
                for (int k = 0; k < D2[i][j].length; k++) {
                    D2[i][j][k] = -1;
                }
            }
        }

        _editDistance(A.length, B.length, C.length);

//        debugPrint(A,B);
//        debugPrint(B,C);
        return D2[A.length][B.length][C.length];
//        D = new int[a.length + 1][b.length + 1];
//
//        _editDistance(a.length, b.length);
//        List<Integer> sub = reverse(A, B);
//        debugPrint(A, B);
//        System.out.println(sub);
//        A = sub.stream().mapToInt(it -> it).toArray();
//        B = c;
//        D = new int[A.length + 1][B.length + 1];
//        _editDistance(A.length, B.length);
//
//        return D[A.length][B.length];
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
        int min = Math.max(ins, Math.max(del, sub));
        D[i][j] = min;
        return min;
    }

    private static int _editDistance(int i, int j, int k) {
        if (i == 0 || j == 0 || k == 0) {
            D2[i][j][k] = 0;
            return 0;
        }
        if (D2[i][j][k] != -1) {
            return D2[i][j][k];
        }

        int ins1 = _editDistance(i - 1, j, k);
        int ins2 = _editDistance(i, j - 1, k);
        int ins3 = _editDistance(i, j, k - 1);
        int sub = _editDistance(i - 1, j - 1, k - 1) + diff(A[i - 1], B[j - 1], C[k - 1]);
        int min = Math.max(ins1, Math.max(ins2, Math.max(ins3, sub)));
        D2[i][j][k] = min;
        return min;
    }

    private static int diff(int a, int b) {
        if (a == b) {
            return 1;
        }
        return Integer.MIN_VALUE;
    }

    private static int diff(int a, int b, int c) {
        if (a == b && b == c) {
            return 1;
        }
        return Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int an = scanner.nextInt();
        int[] a = new int[an];
        for (int i = 0; i < an; i++) {
            a[i] = scanner.nextInt();
        }
        int bn = scanner.nextInt();
        int[] b = new int[bn];
        for (int i = 0; i < bn; i++) {
            b[i] = scanner.nextInt();
        }
        int cn = scanner.nextInt();
        int[] c = new int[cn];
        for (int i = 0; i < cn; i++) {
            c[i] = scanner.nextInt();
        }
        System.out.println(lcs3(a, b, c));
    }
}


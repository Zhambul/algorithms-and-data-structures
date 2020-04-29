import java.util.*;

class EditDistance {
    static int D[][];
    static String a;
    static String b;

    public static int editDistance(String s, String t) {
        //write your code here
        D = new int[s.length() + 1][t.length() + 1];
        a = s;
        b = t;
        _editDistance(s.length(), t.length());
        return D[s.length()][t.length()];
    }

    private static int _editDistance(int i, int j) {
        if (i == 0) {
            return j;
        }
        if (j == 0) {
            return i;
        }
        if (D[i][j] != 0) {
            return D[i][j];
        }
        int ins = _editDistance(i - 1, j) + 1;
        int del = _editDistance(i, j - 1) + 1;
        int sub = _editDistance(i - 1, j - 1) + diff(a.charAt(i - 1), b.charAt(j - 1));
        int min = Math.min(ins, Math.min(del, sub));
        D[i][j] = min;
        return min;
    }

    private static int diff(char a, char b) {
        if (a == b) {
            return 0;
        }
        return 1;
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);

        String s = scan.next();
        String t = scan.next();

        System.out.println(editDistance(s, t));
    }

}

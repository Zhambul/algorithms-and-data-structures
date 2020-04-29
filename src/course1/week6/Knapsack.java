import java.util.*;

public class Knapsack {
    static int[][] D;
    static int[] items;

    static int optimalWeight(int W, int[] w) {
        //write you code here
        D = new int[W + 1][w.length + 1];
        items = w;

        for (int i = 1; i < W + 1; i++) {
            for (int j = 0; j < w.length; j++) {
                _optimalWeight(i, j);
            }
        }

        return D[W][w.length - 1];
    }

    private static int _optimalWeight(int w, int itemNum) {
        int itemW = items[itemNum];
        if (itemNum == 0 && itemW <= w) {
            D[w][itemNum] = itemW;
            return itemW;
        }
        int with = 0;
        if (itemW <= w) {
            with = D[w - itemW][itemNum - 1] + itemW;
        }
        int without = 0;
        if (itemNum > 0) {
            without = D[w][itemNum - 1];
        }
        int max = Math.max(with, without);
        D[w][itemNum] = max;
        return max;
    }

//
//    private static int _optimalWeight(int W) {
//        if (D[W] != 0) {
//            return D[W];
//        }
//
//        int max = 0;
//
//        for (int w : items) {
//            if (w <= W) {
//                int we = _optimalWeight(W - w, items) + w;
//                max = Math.max(max, we);
//            }
//        }
//
//        D[W] = max;
//        return max;
//    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int W, n;
        W = scanner.nextInt();
        n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }
        System.out.println(optimalWeight(W, w));
    }
}


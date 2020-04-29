import java.util.*;

public class Inversions {

    private static long getNumberOfInversions(int[] a, int[] b, int left, int right) {
        long numberOfInversions = 0;
        if (right <= left + 1) {
            return numberOfInversions;
        }
        int ave = (left + right) / 2;
        numberOfInversions += getNumberOfInversions(a, b, left, ave);
        numberOfInversions += getNumberOfInversions(a, b, ave, right);
        //write your code here
        return numberOfInversions;
    }

    private static void countInv(int[] array1, int[] array2) {
        for (int i = 0; i < array1.length; i++) {
            for (int j = array2.length - 1; j >= 0; j--) {
                if (array1[i] > array2[j]) {
                    inv += j + 1;
                    break;
                }
            }
        }
    }

    private static int[] mergeSort(int[] a) {
        if (a.length == 1) {
            return a;
        }
        int m = a.length / 2;
        return merge(mergeSort(Arrays.copyOfRange(a, 0, m)), mergeSort(Arrays.copyOfRange(a, m, a.length)));
    }

    private static int inv = 0;


    private static int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int aP = 0;
        int bP = 0;

        for (int i = 0; i < res.length; i++) {
            if (aP < a.length && bP < b.length) {
                if (a[aP] < b[bP]) {
                    res[i] = a[aP];
                    aP++;
                } else {
                    res[i] = b[bP];
                    bP++;
                }
            } else if (aP < a.length) {
                res[i] = a[aP];
                aP++;
            } else {
                res[i] = b[bP];
                bP++;
            }
        }
        countInv(a, b);

        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        mergeSort(a);
        System.out.println(inv);
    }
}


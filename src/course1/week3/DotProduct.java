import javax.swing.*;
import java.util.*;

public class DotProduct {
    private static long maxDotProduct(long[] cs, long[] cls) {
        //write your code here
        long result = 0;
        List<Long> costs = new ArrayList<>();
        List<Long> clicks = new ArrayList<>();
        for (int i = 0; i < cs.length; i++) {
            costs.add(cs[i]);
        }
        for (int i = 0; i < cls.length; i++) {
            clicks.add(cls[i]);
        }
        Collections.sort(costs);
        Collections.sort(clicks);
        while (!clicks.isEmpty()) {
            long maxCos = costs.remove(costs.size() - 1);
            long maxClick = clicks.remove(clicks.size() - 1);
            result += maxCos * maxClick;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextLong();
        }
        long[] b = new long[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextLong();
        }
        System.out.println(maxDotProduct(a, b));
    }
}


import java.util.*;
import java.util.stream.Collectors;

public class LargestNumber {
    private static String largestNumber(String[] a) {
        //write your code here
        Arrays.sort(a, (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            int a1 = Integer.parseInt(o1 + o2);
            int b = Integer.parseInt(o2 + o1);
            if (a1 > b) {
                return -1;
            }
            return 1;
        });
        return Arrays.stream(a).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] a = new String[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.next();
        }
        System.out.println(largestNumber(a));
    }
}


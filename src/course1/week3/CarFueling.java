import java.util.*;
import java.io.*;

public class CarFueling {
    static int computeMinRefills(int dist, int tank, int[] stops) {
        int prevStop = 0;
        int curStop = 0;
        int refills = 0;
        int curPos = 0;
        int curTank = tank;
        List<Integer> s = new ArrayList<>();
        for (int stop : stops) {
            s.add(stop);
        }

        while (true) {
            if (curPos + curTank >= dist) {
                return refills;
            }
            if (!s.isEmpty()) {
                prevStop = curStop;
                curStop = s.get(0);
                if (curStop - prevStop > tank) {
                    return -1;
                }
                int distToTravel = curStop - curPos;
                if (distToTravel <= curTank) {
                    s.remove(0);
                    curTank -= distToTravel;
                    curPos = curStop;
                    continue;
                }
            } else {
                prevStop = curStop;
                if (dist - curStop > tank) {
                    return -1;
                }
            }
            curPos = prevStop;
            curTank = tank;
            refills++;

            if (refills > stops.length) {
                throw new RuntimeException("asdads");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dist = scanner.nextInt();
        int tank = scanner.nextInt();
        int n = scanner.nextInt();
        int stops[] = new int[n];
        for (int i = 0; i < n; i++) {
            stops[i] = scanner.nextInt();
        }

        System.out.println(computeMinRefills(dist, tank, stops));
    }
}

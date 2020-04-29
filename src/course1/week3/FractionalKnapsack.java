import java.util.*;

public class FractionalKnapsack {
    static class Item {
        double cost;
        double weight;

        public Item(double cost, double weight) {
            this.cost = cost;
            this.weight = weight;
        }
    }

    private static double getOptimalValue(int capacity, double[] values, double[] weights) {
        double curCapacity = capacity;
        double curValue = 0;
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            items.add(new Item(values[i], weights[i]));
        }

        items.sort(Comparator.comparing((Item item) -> item.cost / item.weight).reversed());

        while (!items.isEmpty()) {
            Item item = items.remove(0);
            if (item.weight <= curCapacity) {
                curCapacity -= item.weight;
                curValue += item.cost;
            } else {
                curValue += item.cost * (curCapacity / item.weight);
                curCapacity = 0;
            }
        }
        return curValue;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        double[] values = new double[n];
        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextDouble();
            weights[i] = scanner.nextDouble();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
}

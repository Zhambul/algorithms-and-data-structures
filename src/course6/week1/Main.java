package course6.week1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        System.out.println(getMaxOverlap("ATCG", "TCGA"));
        FastScanner scanner = new FastScanner(System.in);
        List<String> input = new ArrayList<>();
        while (true) {
            String next = scanner.next();
            if (next == null) {
                break;
            }
            input.add(next);
        }
        String result = assemble(input);
        System.out.println(result);
    }

    static class Node {
        private final String value;
        boolean assembled;
        List<Edge> next = new ArrayList<>();
        List<Edge> prev = new ArrayList<>();

        Node(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" + value +
                    '}';
        }
    }

    static class Edge {
        final Node to;
        final int weight;

        Edge(Node to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private static String assemble(List<String> input) {
        Map<String, Node> map = constructGraph(input);
        return assemble(map);
    }

    private static String assemble(Map<String, Node> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Node> entry : map.entrySet()) {
            if (entry.getValue().assembled) {
                continue;
            }
            Node node = entry.getValue();
//            Edge edge = node.next.stream().max(Comparator.comparingInt(e -> e.weight)).get();
//            String merge
        }
        return builder.toString();
    }

    private static Map<String, Node> constructGraph(List<String> input) {
        Map<String, Node> map = new HashMap<>();
        for (String s : input) {
            map.put(s, new Node(s));
        }
        List<List<String>> pairs = doPairs(input);
        for (List<String> pair : pairs) {
            String first = pair.get(0);
            String second = pair.get(1);
            int overlap = getMaxOverlap(first, second);
            if (overlap > 0) {
                Node firstNode = map.get(first);
                Node secondNode = map.get(second);
                firstNode.next.add(new Edge(secondNode, overlap));
                secondNode.prev.add(new Edge(firstNode, overlap));
            }
        }
        return map;
    }

    private static int getMaxOverlap(String a1, String b1) {
//        char[] a = a1.toCharArray();
//        char[] b = b1.toCharArray();
//        int max = Integer.MIN_VALUE;
//        LinkedList<Character> list = new LinkedList<>();
//        for (char c : b) {
//            list.add(c);
//        }
//        for (int i = 0; i < b.length - 1; i++) {
//            shiftRight(list);
//            int overlap = getOverlap(a, list);
//            max = Math.max(overlap, max);
//        }
        return Math.max(getO(a1, b1), getO(b1, a1));
    }

    private static int getO(String a, String b) {
        int max = 0;
        for (int i = 1; i < a.length(); i++) {
            String prefix = a.substring(0, i);
            if (b.endsWith(prefix)) {
                max = i;
            }
        }
        return max;
    }

    private static void shiftRight(LinkedList<Character> list) {
        Character tail = list.pollLast();
        list.addFirst(tail);
    }

    private static int getOverlap(char[] a, LinkedList<Character> list) {
        int overlap = 0;
        int i = 0;
        for (Character character : list) {
            if (a[i] == character) {
                overlap++;
            }
            i++;
        }
        return overlap;
    }

    private static List<List<String>> doPairs(List<String> input) {
        List<List<String>> result = new ArrayList<>();
        while (input.size() > 1) {
            String head = input.remove(0);
            for (String next : input) {
                List<String> pair = new ArrayList<>(2);
                pair.add(next);
                pair.add(head);
                result.add(pair);
            }
        }
        return result;
    }


    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            try {
                if (!br.ready()) {
                    return null;
                }
                while (st == null || !st.hasMoreTokens()) {
                    try {
                        st = new StringTokenizer(br.readLine());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static class Edge {
        char value;
        Node node;
    }

    static int nodeId;

    static class Node {
        final int index;
        List<Edge> next = new ArrayList<>();

        Node() {
            this.index = nodeId;
            nodeId++;
        }

        void insert(String pattern) {
            insert(pattern, 0);
        }

        void insert(String pattern, int from) {
            if (from == pattern.length()) {
                return;
            }
            char c = pattern.charAt(from);
            for (Edge edge : next) {
                if (edge.value == c) {
                    edge.node.insert(pattern, from + 1);
                    return;
                }
            }
            Node n = new Node();
            Edge edge = new Edge();
            edge.value = c;
            edge.node = n;
            next.add(edge);
            n.insert(pattern, from + 1);
        }


        void print() {
            for (Edge edge : next) {
                System.out.println(index + "->" + edge.node.index + ":" + edge.value);
            }
            for (Edge edge : next) {
                edge.node.print();
            }
        }
    }

    void buildTrie(String[] patterns) {
        Node root = new Node();

        for (String pattern : patterns) {
            root.insert(pattern);
        }
        root.print();
    }

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        buildTrie(patterns);
    }
}

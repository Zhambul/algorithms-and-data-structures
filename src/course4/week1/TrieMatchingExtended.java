import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class TrieMatchingExtended {
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
        boolean patternEnd;
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
                    if (from + 1 == pattern.length()) {
                        edge.patternEnd = true;
                    }
                    edge.node.insert(pattern, from + 1);
                    return;
                }
            }
            Node n = new Node();
            Edge edge = new Edge();
            edge.value = c;
            edge.node = n;
            next.add(edge);
            if (from + 1 == pattern.length()) {
                edge.patternEnd = true;
            }
            n.insert(pattern, from + 1);
        }

        void search(String text) {
            for (int i = 0; i < text.length(); i++) {
                final int j = i;
                if (search(text, 0, i)) {
                    System.out.print(i + " ");
//                    System.out.print(matches.stream().map(e -> e + j).map(String::valueOf).collect(Collectors.joining(" ")) + " ");
                }
            }
        }

        boolean search(String text, int from, int add) {
            if (next.isEmpty()) {
                return true;
            }

            if (text.length() == from + add) {
                return false;
            }

            char c = text.charAt(from + add);
            for (Edge edge : next) {
                if (edge.value == c) {
                    if (edge.patternEnd) {
                        return true;
                    }
                    return edge.node.search(text, from + 1, add);
                }
            }

            return false;
        }
    }

    void buildTrie(String text, String[] patterns) {
        Node root = new Node();

        for (String pattern : patterns) {
            root.insert(pattern);
        }

        root.search(text);
    }

    static public void main(String[] args) throws IOException {
        new TrieMatchingExtended().run();
    }


    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        buildTrie(text, patterns);
    }
}

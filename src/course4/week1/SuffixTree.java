import java.util.*;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
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
        //        String value;
        int from;
        int to;
        Node node;
    }

    static int nodeId;

    static class Node {
        final int index;
        Edge prev;
        List<Edge> next = new ArrayList<>();

        Node() {
            this.index = nodeId;
            nodeId++;
        }
    }

    void insertText(Node node, String text, int from, int add) {
        while (true) {
            if (from + add == text.length()) {
                break;
            }
//            char c = text.charAt(from + add);
            boolean found = false;
            for (Edge edge : node.next) {
                if (text.charAt(edge.from) == text.charAt(from + add)) {
                    from++;
                    node = edge.node;
                    found = true;
                    break;
                }
            }

            if (found) {
                continue;
            }

            Node n = new Node();
            Edge edge = new Edge();
//            edge.value = String.valueOf(c);
            edge.from = from + add;
            edge.to = from + add + 1;
            edge.node = n;
            node.next.add(edge);
            n.prev = edge;
            from++;
            node = n;
        }
    }

    void optimize(Node node) {
        Stack<Node> nodes = new Stack<>();
        nodes.add(node);
        while (!nodes.isEmpty()) {
            node = nodes.pop();
            if (node.next.size() == 1) {
                Edge edge = node.next.get(0);
//                node.prev.value += edge.value;
                node.prev.to = edge.to;
                node.next = edge.node.next;
                nodes.add(node);
                continue;
            }
            for (Edge edge : node.next) {
                nodes.add(edge.node);
            }
        }
    }

    void print(Node node, String text) {
        Stack<Node> nodes = new Stack<>();
        nodes.add(node);
        while (!nodes.isEmpty()) {
            node = nodes.pop();
            for (Edge edge : node.next) {
                System.out.println(text.substring(edge.from, edge.to));
            }
            for (Edge edge : node.next) {
                nodes.add(edge.node);
            }
        }
    }

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding
    // substrings of the text) in any order.
    public void computeSuffixTreeEdges(String text) {
        Node root = new Node();
        for (int i = 0; i < text.length(); i++) {
            insertText(root, text, 0, i);
        }
        optimize(root);
        print(root, text);
    }


    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        computeSuffixTreeEdges(text);
    }
}

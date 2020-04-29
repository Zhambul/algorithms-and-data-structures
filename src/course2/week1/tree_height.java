import java.util.*;
import java.io.*;

public class tree_height {
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

    public class Node {
        List<Node> children = new ArrayList<>();

        public int getMaxHeight(int level) {
            int max = level;
            for (Node child : children) {
                max = Math.max(max, child.getMaxHeight(level + 1));
            }
            return max;
        }
    }


    public class TreeHeight {
        int n;
        int parent[];
        List<Node> nodes = new ArrayList<>();
        Node root = null;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = in.nextInt();
                Node node = new Node();
                nodes.add(node);

                if (parent[i] == -1) {
                    root = node;
                }
            }
            for (int i = 0; i < n; i++) {
                int par = parent[i];
                if (par != -1) {
                    nodes.get(par).children.add(nodes.get(i));
                }
            }
        }

        int computeHeight() {
            return root.getMaxHeight(1);
//            int root = -1;
//            for (int i = 0; i < parent.length; i++) {
//                if (parent[i] == -1) {
//                    root = i;
//                }
//            }
//            return getMax(parent, root, 1);
        }

        private int getMax(int[] parent, int root, int level) {
            int max = level;

            for (int i = 0; i < parent.length; i++) {
                if (parent[i] == root) {
                    max = Math.max(max, getMax(parent, i, level + 1));
                }
            }
            return max;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, () -> {
            try {
                new tree_height().run();
            } catch (IOException e) {
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}

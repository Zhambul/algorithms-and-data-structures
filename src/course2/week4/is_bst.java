import java.util.*;
import java.io.*;

public class is_bst {
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

    public class IsBST {
        class Node {
            int key;
            int left;
            int right;

            Node(int key, int left, int right) {
                this.left = left;
                this.right = right;
                this.key = key;
            }
        }

        int nodes;
        Node[] tree;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            nodes = in.nextInt();
            tree = new Node[nodes];
            for (int i = 0; i < nodes; i++) {
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());
            }
        }

        boolean isBinarySearchTree() {
            // Implement correct algorithm here
            if (tree.length == 0) {
                return true;
            }
            return check(tree[0]);
        }

        private boolean check(Node node) {
            Node next = getNext(node);
            if (next != null && next.key < node.key) {
                return false;
            }
            Node prev = getPrev(node);
            if (prev != null && prev.key >= node.key) {
                return false;
            }
            if (node.right != -1 && !check(tree[node.right])) {
                return false;
            }
            if (node.left != -1 && !check(tree[node.left])) {
                return false;
            }

            return true;
//            if (node.left != -1) {
//                if (tree[node.left].key >= node.key) {
//                    return false;
//                }
//                if (!check(tree[node.left])) {
//                    return false;
//                }
//            }
//            if (node.right != -1) {
//                if (tree[node.right].key < node.key) {
//                    return false;
//                }
//                if (!check(tree[node.right])) {
//                    return false;
//                }
//            }
//            return true;
        }

        private Node getPrev(Node node) {
            if (node.left == -1) {
                return null;
            }
            return getRightest(tree[node.left]);
        }

        private Node getRightest(Node node) {
            if (node.right == -1) {
                return node;
            }
            return getRightest(tree[node.right]);
        }

        private Node getNext(Node node) {
            if (node.right == -1) {
                return null;
            }
            return getLeftest(tree[node.right]);
        }

        private Node getLeftest(Node node) {
            if (node.left == -1) {
                return node;
            }
            return getLeftest(tree[node.left]);
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new is_bst().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        IsBST tree = new IsBST();
        tree.read();
        if (tree.isBinarySearchTree()) {
            System.out.println("CORRECT");
        } else {
            System.out.println("INCORRECT");
        }
    }
}

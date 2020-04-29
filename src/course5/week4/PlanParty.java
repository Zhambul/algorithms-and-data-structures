import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Vertex {
    Vertex(int index) {
        this.index = index;
        this.weight = 0;
        this.children = new ArrayList<>();
    }

    final int index;
    int weight;
    List<Vertex> children;
    Vertex parent;
    boolean visited;

    @Override
    public String toString() {
        return "Vertex{" +
                (index + 1) +
                '}';
    }
}

class PlanParty {
    static Vertex[] ReadTree() throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        StreamTokenizer tokenizer = new StreamTokenizer(reader);

        tokenizer.nextToken();
        int vertices_count = (int) tokenizer.nval;
        D = new long[vertices_count];
        Vertex[] tree = new Vertex[vertices_count];

        for (int i = 0; i < vertices_count; ++i) {
            tree[i] = new Vertex(i);
            tokenizer.nextToken();
            tree[i].weight = (int) tokenizer.nval;
        }

        for (int i = 1; i < vertices_count; ++i) {
            tokenizer.nextToken();
            int from = (int) tokenizer.nval - 1;
            tokenizer.nextToken();
            int to = (int) tokenizer.nval - 1;
            Vertex boss = tree[from];
            Vertex emp = tree[to];
            if (emp.parent != null) {
                emp.children.add(boss);
                boss.parent = emp;
            } else {
                boss.children.add(emp);
                emp.parent = boss;
            }
        }


        return tree;
    }

    static long[] D;

    static long getFun(Vertex vertex) {
        if (D[vertex.index] != 0) {
            return D[vertex.index];
        }
        long grandChildrenFun = getGrandchildrenFun(vertex);
        long childrenFun = getChildrenFun(vertex);
        long with = vertex.weight + grandChildrenFun;
        long result = Math.max(with, childrenFun);
        D[vertex.index] = result;
        return result;
    }

    static long getGrandchildrenFun(Vertex vertex) {
        long sum = 0;
        for (Vertex child : vertex.children) {
            sum += getChildrenFun(child);
        }
        return sum;
    }

    static long getChildrenFun(Vertex vertex) {
        long sum = 0;

        for (Vertex child : vertex.children) {
            sum += getFun(child);
        }

        return sum;
    }

    static long MaxWeightIndependentTreeSubset(Vertex[] tree) {
        int size = tree.length;
        if (size == 0)
            return 0;
        Vertex root = getRoot(tree);
        while (true) {
            Vertex leaf = getLeaf(root);
            if (leaf == null) {
                break;
            }
            getFun(leaf);
        }
        return getFun(root);
    }

    static Vertex getRoot(Vertex[] tree) {
        return getRoot(tree[0]);
    }

    static Vertex getRoot(Vertex vertex) {
        if (vertex.parent != null) {
            return getRoot(vertex.parent);
        }
        return vertex;
    }

    static Vertex getLeaf(Vertex vertex) {
        if (vertex.visited) {
            return null;
        }
        vertex.visited = true;
        for (Vertex child : vertex.children) {
            if (!child.visited) {
                return getLeaf(child);
            }
        }
        return vertex;
    }

    public static void main(String[] args) throws IOException {
        // This is to avoid stack overflow issues
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new PlanParty().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        Vertex[] tree = ReadTree();
        long weight = MaxWeightIndependentTreeSubset(tree);
        System.out.println(weight);
    }
}

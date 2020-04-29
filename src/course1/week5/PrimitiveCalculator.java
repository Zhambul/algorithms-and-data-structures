import java.util.*;

public class PrimitiveCalculator {

    static Map<Integer, List<Integer>> map = new HashMap<>();

    private static List<Integer> optimal_sequence(int n) {
//        long l = System.nanoTime();
        if (n == 1) {
            return Collections.singletonList(1);
        }
        map.put(1, Collections.singletonList(1));
        Node oneNode = new Node(1);
        oneNode.depth++;
        cache.put(1, oneNode);

        int cachedNodeNumber = n;
        if (n % 3 == 0) {
            cachedNodeNumber = cachedNodeNumber / 3;
        } else if ((n - 1) % 3 == 0)  {
            cachedNodeNumber = cachedNodeNumber / 3;
        } else{
            cachedNodeNumber = cachedNodeNumber / 2;
        }

        for (int i = 2; i < cachedNodeNumber; i++) {
            new Node(i).sequence();
        }
        List<Integer> seq = new ArrayList<>();

        Node node = new Node(n);
        node.sequence();
        seq.add(node.v);
        Node next = null;

        while (true) {
            next = node.next;
            if (next == null) {
                break;
            }
            seq.add(next.v);
            node = next;
        }

        Collections.reverse(seq);
//        long r = System.nanoTime();
//        System.out.println((r - l) / 1000);
        return seq;
    }

    static Map<Integer, Node> cache = new HashMap<>();

    static int maxDepth = 5;

    static class Node {
        int v;
        int depth;
        Node next;

        public Node(int value) {
            this.v = value;
        }

        // returns next
        void sequence() {
            if (v % 3 == 0) {
                next = nextSeq(v / 3);
                putInCache();
                return;
            }
            if (v % 2 != 0) {
                next = nextSeq(v - 1);
                putInCache();
                return;
            }
            if (depth > maxDepth) {
                next = nextSeq(v / 2);
                putInCache();
                return;
            }

            Node two = nextSeq(v / 2);
            Node one = nextSeq(v - 1);
            if (one.depth < two.depth) {
                next = one;
                putInCache();
                return;
            }
            next = two;
            putInCache();
        }

        private void putInCache() {
            depth = next.depth + 1;
            cache.put(v, this);
        }

        private Node nextSeq(int n) {
            Node c = cache.get(n);
            if (c != null) {
                return c;
            }
            Node child = new Node(n);
            child.sequence();
            return child;
        }
    }


    private static List<Integer> _optimal_sequenceUB(int n) {
        if (map.get(n) != null) {
            return new ArrayList<>(map.get(n));
        }
        List<Integer> a = null;
        List<Integer> b = null;
        List<Integer> c = null;
        if (n % 3 == 0) {
            b = _optimal_sequenceUB(n / 3);
            b.add(n);
        } else {
            if (n % 2 == 0) {
                c = _optimal_sequenceUB(n / 2);
                c.add(n);
            }
            a = _optimal_sequenceUB(n - 1);
            a.add(n);
        }

        int aSize = Integer.MAX_VALUE;
        if (a != null) {
            aSize = a.size();
        }
        int bSize = Integer.MAX_VALUE;
        if (b != null) {
            bSize = b.size();
        }

        int cSize = Integer.MAX_VALUE;
        if (c != null) {
            cSize = c.size();
        }
        int min = Math.min(aSize, Math.min(bSize, cSize));
        if (aSize == min) {
            map.put(n, a);
        } else if (bSize == min) {
            map.put(n, b);
        } else {
            map.put(n, c);
        }
        return new ArrayList<>(map.get(n));
    }

    private static int _optimal_sequence(int n) {
        int a = n - 1;
        int b = Integer.MAX_VALUE;
        if (n % 2 == 0) {
            b = n / 2;
        }
        int c = Integer.MAX_VALUE;
        if (n % 3 == 0) {
            b = n / 3;
        }
        return Math.min(a, Math.min(b, c));
    }

    private static void _optimal_sequence2(int n, int max) {
        int a = n + 1;
        int b = n * 2;
        int c = n * 3;
        List<Integer> r = map.remove(n);
        if (b <= max) {
            List<Integer> list = new ArrayList<>(r);
            list.add(b);
            map.put(b, list);
        }
        if (c <= max) {
            List<Integer> list = new ArrayList<>(r);
            list.add(c);
            map.put(c, list);
        }
        List<Integer> list = new ArrayList<>(r);
        list.add(a);
        map.put(a, list);
    }

    private static int _optimal_sequenceBU(int n, int max) {
        List<Integer> left = map.get(n);
        int a = n + 1;
        int b = n * 2;
        int c = n * 3;
        List<Integer> al = new ArrayList<>(left);
        al.add(a);
        List<Integer> bl = new ArrayList<>(left);
        bl.add(b);
        List<Integer> cl = new ArrayList<>(left);
        cl.add(c);

        putIfShorter(a, al);
        putIfShorter(b, bl);
        putIfShorter(c, cl);

        al = map.get(a);
        bl = map.get(b);
        cl = map.get(c);
        int alSize = al.size();
        int blSize = bl.size();
        int clSize = cl.size();
        int min = Math.min(alSize, Math.min(blSize, clSize));
        if (clSize == min) {
            return c;
        }
        if (blSize == min) {
            return b;
        }
        return a;
//        if (c <= max) {
//            return c;
//        }
//        if (b <= max) {
//            return b;
//        }
//        return a;
    }

    private static void putIfShorter(int a, List<Integer> al) {
        map.compute(a, (integer, integers) -> {
            if (integers == null) {
                return al;
            }
            if (integers.size() <= al.size()) {
                return integers;
            }
            return al;
        });
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
}


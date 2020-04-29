
import java.util.*;
import java.util.function.BiConsumer;

public class Clustering {

    static class Point {
        final int x;
        final int y;
        Set<Point> component = new HashSet<>();

        Point(int x, int y) {
            this.x = x;
            this.y = y;
            component.add(this);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    static class Edge {
        final Point from;
        final Point to;
        private double weight = -1;

        Edge(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        public double getWeight() {
            if (weight != -1) {
                return weight;
            }
            weight = Math.sqrt(Math.pow(from.x - to.x, 2) + Math.pow(from.y - to.y, 2));
            return weight;
        }

        @Override
        public String toString() {
            return "from " + from + " to " + to + ". w = " + getWeight();
        }
    }

    static List<Point> points = new ArrayList<>();
    static List<Edge> edges = new ArrayList<>();

    static DisjointSet<Point> set = new DisjointSet<>();

    private static double gitMinimumSumOfEdges(int k) {
        List<Edge> result = new ArrayList<>();
        initEdges();
        int components = points.size();

        Iterator<Edge> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge edge = iterator.next();

            if (components == k) {
                break;
            }
            Point from = edge.from;
            Point to = edge.to;

            if (set.find(from) != set.find(to)) {
                set.union(from, to);
                result.add(edge);
                components--;
            }

            iterator.remove();
        }
        Edge next = null;

        while (iterator.hasNext()) {
            Edge edge = iterator.next();

            Point from = edge.from;
            Point to = edge.to;

            if (set.find(from) != set.find(to)) {
                next = edge;
                break;
            }
        }
        return next.getWeight();
//
//        List<Edge> left = new ArrayList<>(edges);
//
//
//        List<Edge> leftBetweenComponents = new ArrayList<>();
//        left.removeAll(result);
//
//        for (Edge edge : left) {
//            if (set.find(edge.from) != set.find(edge.to)) {
//                leftBetweenComponents.add(edge);
//            }
//        }
//
//
//        for (Edge leftBetweenComponent : leftBetweenComponents) {
//            System.out.println(leftBetweenComponent.getWeight());
//        }


//        Map<Point, List<Point>> clusters = getClusters();
//
//        clusters.forEach((point, points) -> {
//            System.out.println("New cluster");
//            for (Point point1 : points) {
//                System.out.println(point1);
//            }
//            System.out.println();
//            System.out.println();
//        });
//
//        return getD(new ArrayList<>(clusters.values()));



//        return result.stream().mapToDouble(Edge::getWeight).sum();
    }

    private static double getD(List<List<Point>> clusters) {
        double d = Double.MIN_VALUE;

        Iterator<List<Point>> iterator = clusters.iterator();
        while (iterator.hasNext()) {
            List<Point> cluster = iterator.next();
            for (List<Point> cl : clusters) {
                if (cluster == cl) {
                    continue;
                }
                d = Math.max(d, getD(cluster, cl));
            }
            iterator.remove();
        }
        return d;
    }

    private static double getD(List<Point> cl1, List<Point> cl2) {
        double distance = Double.MAX_VALUE;
        for (Point p1 : cl1) {
            for (Point p2 : cl2) {
                distance = Math.min(distance, new Edge(p1, p2).getWeight());
            }
        }
        return distance;
    }

    private static Map<Point, List<Point>> getClusters() {
        Map<Point, List<Point>> com = new HashMap<>();

        for (Point point : points) {
            boolean foundGroup = false;
            for (Map.Entry<Point, List<Point>> e : com.entrySet()) {
                if (set.find(e.getKey()) == set.find(point)) {
                    e.getValue().add(point);
                    foundGroup = true;
                    break;
                }
            }

            if (foundGroup) {
                continue;
            }

            List<Point> p = new ArrayList<>();
            p.add(point);
            com.put(point, p);
        }
        return com;
    }

    private static void initEdges() {
        List<Point> p = new ArrayList<>(points);

        Iterator<Point> iterator = p.iterator();
        while (iterator.hasNext()) {
            Point next = iterator.next();
            for (Point point : p) {
                if (next == point) {
                    continue;
                }
                edges.add(new Edge(next, point));
            }
            iterator.remove();
        }

        edges.sort(Comparator.comparingDouble(Edge::getWeight));
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }
        int k = scanner.nextInt();
        System.out.println(gitMinimumSumOfEdges(k));
    }

    static final class DisjointSet<E> {

        /**
         * The map to store the related {@link DisjointSetNode} for each added element.
         */
        private final Map<E, DisjointSetNode<E>> disjointSets = new HashMap<E, DisjointSetNode<E>>();

        /**
         * Performs the {@code find} operation applying the <i>path compression</i>.
         *
         * @param e the element has to be find in this {@code DisjointSet} instance
         * @return the value found
         */
        public E find(E e) {
            DisjointSetNode<E> node = find(getNode(e));

            if (node == node.getParent()) {
                return node.getElement();
            }

            node.setParent(find(node.getParent()));

            return node.getParent().getElement();
        }

        /**
         * Performs the @code{ find} operation by applying the <i>path compression</i>.
         *
         * @param node the input DisjointSet node for the @code{ find} operation
         * @return the root node of the path
         */
        private DisjointSetNode<E> find(DisjointSetNode<E> node) {
            if (node == node.getParent()) {
                return node;
            }
            return find(node.getParent());
        }

        /**
         * Join two subsets into a single subset, performing the merge by applying the <i>union by rank</i>.
         *
         * @param e1 the first element which related subset has to be merged
         * @param e2 the second element which related subset has to be merged
         */
        public void union(E e1, E e2) {
            DisjointSetNode<E> e1Root = find(getNode(e1));
            DisjointSetNode<E> e2Root = find(getNode(e2));

            if (e1Root == e2Root) {
                return;
            }

            int comparison = e1Root.compareTo(e2Root);
            if (comparison < 0) {
                e1Root.setParent(e2Root);
            } else if (comparison > 0) {
                e2Root.setParent(e1Root);
            } else {
                e2Root.setParent(e1Root);
                e1Root.increaseRank();
            }
        }

        /**
         * Retrieves the {@code DisjointSetNode} from the {@link #disjointSets},
         * if already previously set, creates a new one and push it in {@link #disjointSets} otherwise.
         *
         * @param e the element which related subset has to be returned
         * @return the input element {@code DisjointSetNode}
         */
        private DisjointSetNode<E> getNode(E e) {
            DisjointSetNode<E> node = disjointSets.get(e);

            if (node == null) {
                node = new DisjointSetNode<E>(e);
                disjointSets.put(e, node);
            }

            return node;
        }

    }

    static final class DisjointSetNode<E>
            implements Comparable<DisjointSetNode<E>> {

        /**
         * The stored node value.
         */
        private final E element;

        /**
         * The {@code DisjointSetNode} parent node, {@code this} by default.
         */
        private DisjointSetNode<E> parent = this;

        /**
         * The current node rank.
         */
        private Integer rank = 0;

        /**
         * Creates a new {@link DisjointSet} node with the given value.
         *
         * @param element the node value has to be stored.
         */
        public DisjointSetNode(E element) {
            this.element = element;
        }

        /**
         * Returns the adapted element by this node.
         *
         * @return the adapted element by this node.
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the reference to the parent node, the node itself by default.
         *
         * @return the reference to the parent node, the node itself by default.
         */
        public DisjointSetNode<E> getParent() {
            return parent;
        }

        /**
         * Sets the reference to a new parent node.
         *
         * @param parent the reference to a new parent node.
         */
        public void setParent(DisjointSetNode<E> parent) {
            this.parent = parent;
        }

        /**
         * Returns this node rank.
         *
         * @return this node rank
         */
        public Integer getRank() {
            return rank;
        }

        /**
         * Increases this node rank.
         */
        public void increaseRank() {
            rank++;
        }

        /**
         * Sets a new different rank.
         *
         * @param rank the new rank to this node.
         */
        public void setRank(int rank) {
            this.rank = rank;
        }

        /**
         * {@inheritDoc}
         */
        public int compareTo(DisjointSetNode<E> o) {
            return rank.compareTo(o.getRank());
        }

    }
}



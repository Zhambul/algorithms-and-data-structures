import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    private LinkedList<String>[] table;

    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int) hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQuery(Query query) {
        LinkedList<String> list;
        if (query.s == null) {
            list = table[query.ind];
        } else {
            list = table[hashFunc(query.s)];
        }
        switch (query.type) {
            case "add":
                if (list == null) {
                    list = new LinkedList<>();
                    table[hashFunc(query.s)] = list;
                }
                if (!list.contains(query.s)) {
                    list.addFirst(query.s);
                }
//                if (!elems.contains(query.s))
//                    elems.add(0, query.s);
                break;
            case "del":
                if (list != null) {
                    list.remove(query.s);
                }

//                if (elems.contains(query.s))
//                    elems.remove(query.s);
                break;
            case "find":
                if (list == null) {
                    writeSearchResult(false);
                    break;
                }
                writeSearchResult(list.contains(query.s));
                break;
            case "check":
                if (list == null) {
                    out.println();
                    break;
                }
                out.println(String.join(" ", list));

//                for (String cur : elems)
//                    if (hashFunc(cur) == query.ind)
//                        out.print(cur + " ");
//                out.println();
//                // Uncomment the following if you want to play with the program interactively.
//                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        table = new LinkedList[bucketCount];
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

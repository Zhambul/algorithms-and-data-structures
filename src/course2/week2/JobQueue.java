import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueue {
    private long numWorkers;
    private long[] jobs;

    private long[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;
    private PriorityQueue<Worker> workers = new PriorityQueue<>(
            Comparator.comparingLong((Worker worker) -> worker.nextFreeTime)
                    .thenComparingLong((Worker worker) -> worker.id)
    );

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextLong();
        int m = (int) in.nextLong();
        jobs = new long[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextLong();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    static class Worker {
        long id;
        long nextFreeTime;

        public Worker(int id) {
            this.id = id;
        }
    }

    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new long[jobs.length];
        startTime = new long[jobs.length];
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Worker(i));
        }
        for (int i = 0; i < jobs.length; i++) {
            long duration = jobs[i];
            Worker worker = workers.poll();
            startTime[i] = worker.nextFreeTime;
            worker.nextFreeTime += duration;
            workers.add(worker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}

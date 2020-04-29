import java.io.IOException;
import java.util.*;

class Request {

    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
        this.timeLeft = this.process_time;
    }

    public int timeLeft;
    public int arrival_time;
    public int process_time;
    public int willFinish;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    Deque<Request> queue = new ArrayDeque<>();
    int curTime = 0;
    Request last;
    int curSize = 0;

    public Buffer(int size) {
        this.maxSize = size;
        this.finish_time_ = new ArrayList<>();
    }

    public Response Process(Request request) {
        curTime = request.arrival_time;
        Iterator<Request> iterator = queue.iterator();

        while (iterator.hasNext()) {
            Request next = iterator.next();
            if (next.willFinish <= curTime) {
                iterator.remove();
            } else {
                break;
            }
        }

        if (queue.size() == maxSize) {
            return new Response(true, -1);
        }

        int willStart;
        Request last = queue.peekLast();
        if (last != null) {
            willStart = last.willFinish;
        } else {
            willStart = curTime;
        }

        queue.add(request);
        request.willFinish = willStart + request.process_time;
        return new Response(false, willStart);

//        int willProcessNext = 0;
//        Request curRequest = queue.peek();
//        if (curRequest != null) {
//            curRequest.timeLeft -= request.arrival_time;
//            willProcessNext = curTime + curRequest.all_process_time;
//            if (curRequest.timeLeft <= 0) {
//                curTime += curRequest.all_process_time;
//                queue.poll();
//            }
//            if (queue.size() == maxSize) {
//                return new Response(true, -1);
//            }
//        }
//        int started = willProcessNext;
//        queue.add(request);
//        return new Response(false, started);

//        curSize++;
//        if (request.arrival_time < curTime) {
//            curSize++;
//        }
//        if (curSize > maxSize) {
//            curSize--;
//            return new Response(true, -1);
//        }
//        int started = curTime;
//        curTime += request.process_time;
//        curSize--;
//        return new Response(false, started);
    }

    private int maxSize;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}

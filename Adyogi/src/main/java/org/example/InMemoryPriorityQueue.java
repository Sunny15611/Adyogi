package org.example;

import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

class Request {
    private static final AtomicLong counter = new AtomicLong(0);
    private final long timestamp;
    private final int priority;
    private final String data;

    public Request(int priority, String data) {
        this.priority = priority;
        this.data = data;
        this.timestamp = counter.getAndIncrement(); // Ensures FCFS ordering
    }

    public int getPriority() {
        return priority;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request{priority=" + priority + ", data='" + data + "'}";
    }
}

public class InMemoryPriorityQueue {
    private final PriorityQueue<Request> queue;

    public InMemoryPriorityQueue() {
        this.queue = new PriorityQueue<>((r1, r2) -> {
            if (r1.getPriority() != r2.getPriority()) {
                return Integer.compare(r2.getPriority(), r1.getPriority()); // Higher priority first
            }
            return Long.compare(r1.getTimestamp(), r2.getTimestamp()); // FCFS ordering
        });
    }

    public void add(int priority, String data) {
        queue.offer(new Request(priority, data));
    }

    public Request poll() {
        return queue.poll();
    }

    public Request peek() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        InMemoryPriorityQueue pq = new InMemoryPriorityQueue();
        pq.add(2, "Request A");
        pq.add(1, "Request B");
        pq.add(3, "Request C");
        pq.add(2, "Request D");
        pq.add(6, "Request E");
        System.out.println("Poll: " + pq.poll());
        System.out.println("Peek: " + pq.peek());
        System.out.println("Size: " + pq.size());

        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }
    }
}

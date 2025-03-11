package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryPriorityQueueTest {
    private InMemoryPriorityQueue queue;

    @BeforeEach
    void setUp() {
        queue = new InMemoryPriorityQueue();
    }

    @Test
    void testAddAndPoll() {
        queue.add(2, "Request A");
        queue.add(1, "Request B");
        queue.add(3, "Request C");

        assertEquals("Request C", queue.poll().getData()); // Highest priority first
        assertEquals("Request A", queue.poll().getData()); // Next highest priority
        assertEquals("Request B", queue.poll().getData()); // Lowest priority
    }

    @Test
    void testPeek() {
        queue.add(4, "Request X");
        assertEquals("Request X", queue.peek().getData());
        assertEquals(1, queue.size()); // Ensure item is not removed
    }

    @Test
    void testSize() {
        queue.add(5, "Request Y");
        queue.add(3, "Request Z");
        assertEquals(2, queue.size());
        queue.poll();
        assertEquals(1, queue.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.add(1, "Request Alpha");
        assertFalse(queue.isEmpty());
    }

    @Test
    void testFCFSForSamePriority() {
        queue.add(2, "First Request");
        queue.add(2, "Second Request");
        assertEquals("First Request", queue.poll().getData());
        assertEquals("Second Request", queue.poll().getData());
    }

    @Test
    void testEmptyPoll() {
        assertNull(queue.poll());
    }
}

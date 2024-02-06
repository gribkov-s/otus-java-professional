package ru.otus.examples.priorityqueue;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityQueueExample {

    public static void main(String[] args) {

        var queue = new PriorityBlockingQueue<Long>();

        var input = new long[]{10, 3, 2, 1};

        for (Long el : input) {
            queue.offer(el);
        }

        var output = new ArrayList<Long>();
        queue.drainTo(output);

        System.out.println(output);

    }
}

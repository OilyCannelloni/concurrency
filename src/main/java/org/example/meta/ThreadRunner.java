package org.example.meta;

import java.util.LinkedList;
import java.util.Map;

public class ThreadRunner {
    private final LinkedList<Thread> threads = new LinkedList<>();

    public ThreadRunner(Map<IThreadFactory, Integer> classMap) {
        for (Map.Entry<IThreadFactory, Integer> e : classMap.entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                Thread thread = e.getKey().create();
                threads.add(thread);
            }
        }
    }

    public void startAll() {
        for (Thread thread : threads)
            thread.start();
    }

    public void joinAll() {
        try {
            for (Thread thread : threads)
                thread.join();
            System.out.println("All threads joined");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

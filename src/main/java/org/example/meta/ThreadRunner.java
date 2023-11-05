package org.example.meta;

import java.util.LinkedList;
import java.util.Map;

public class ThreadRunner {
    private final LinkedList<Thread> _threads = new LinkedList<>();
    private boolean _measureTime = false;
    private long _startTime;

    public ThreadRunner(Map<IThreadFactory, Integer> classMap) {
        for (Map.Entry<IThreadFactory, Integer> e : classMap.entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                Thread thread = e.getKey().create();
                _threads.add(thread);
            }
        }
    }

    public void setMeasureTime(boolean measureTime) {
        _measureTime = measureTime;
    }

    private void printDelta(long delta) {
        StringBuilder s = new StringBuilder();
        while (delta > 0) {
            int frag = (int) (delta % 1000);
            s.insert(0, " ");
            s.insert(0, String.format("%3d", frag).replace(' ', '0'));
            delta /= 1000;
        }

        System.out.printf("Execution time: %sns\n", s.toString().replaceFirst("^0+(?!$)", ""));
    }

    public void startAll() {
        if (_measureTime)
            _startTime = System.nanoTime();

        for (Thread thread : _threads)
            thread.start();
    }

    public void joinAll() {
        try {
            for (Thread thread : _threads)
                thread.join();

            if (_measureTime) {
                long delta = System.nanoTime() - _startTime;
                printDelta(delta);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void joinAny() {
        Thread joiner = new Thread(() -> {
            try {
                boolean threadTerminated = false;
                while (!threadTerminated) {
                    for (Thread thread : _threads) {
                        if (thread.getState() == Thread.State.TERMINATED) {
                            threadTerminated = true;
                            break;
                        }
                    }
                    Thread.sleep(1000);
                }
                for (Thread thread : _threads) {
                    thread.stop();
                }

            } catch (InterruptedException ex) {
                throw new RuntimeException();
            }
        });

        joiner.start();

        if (_measureTime) {
            long delta = System.nanoTime() - _startTime;
            printDelta(delta);
        }
    }
}

package org.example.meta;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class ThreadRunner {
    private final LinkedList<Thread> _threads = new LinkedList<>();
    private boolean _measureTime = false, _measureTimeCPU = false;
    private long _startTime;
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public ThreadRunner(Map<IThreadFactory, Integer> classMap) {
        for (Map.Entry<IThreadFactory, Integer> e : classMap.entrySet()) {
            for (int i = 0; i < e.getValue(); i++) {
                Thread thread = e.getKey().create();
                _threads.add(thread);

            }
        }
    }

    public ThreadRunner setMeasureTime(boolean measureTime) {
        _measureTime = measureTime;
        return this;
    }

    public ThreadRunner setMeasureTimeCPU(boolean measureTimeCPU) {
        if (!threadMXBean.isThreadCpuTimeSupported()){
            System.out.println("Thread CPU time measurement is not suppoertd on this JVM!");
            return this;
        }
        if (!threadMXBean.isThreadCpuTimeEnabled()) {
            System.out.println("Thread CPU time measurement is not enabled!");
            return this;
        }
        if (_threads.size() == 1) {
            System.out.println("Cannot measure CPU time of a single thread!");
            return this;
        }

        _measureTimeCPU = measureTimeCPU;
        return this;
    }

    private String deltaToString(long delta) {
        StringBuilder s = new StringBuilder();
        while (delta > 0) {
            int frag = (int) (delta % 1000);
            s.insert(0, " ");
            s.insert(0, String.format("%3d", frag).replace(' ', '0'));
            delta /= 1000;
        }

        return s.toString().replaceFirst("^0+(?!$)", "");
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
                String d = deltaToString(delta);
                System.out.printf("Excecution time: %sns\n", d);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void awaitJoinAny() {
        // Wykrywamy zakończenie dowolnego wątku
        boolean threadTerminated = false;
        while (!threadTerminated) {
            for (Thread thread : _threads) {
                if (thread.getState() == Thread.State.TERMINATED) {
                    threadTerminated = true;
                    break;
                }
            }
        }

        if (_measureTime) {
            long delta = System.nanoTime() - _startTime;
            System.out.printf("> %-30s%s ns\n", "Real excecution time:", deltaToString(delta));
        }

        // Mierzymy czas CPU
        if (_measureTimeCPU) {
            LinkedList<Long> times = new LinkedList<>();
            for (Thread thread : _threads) {
                long id = thread.getId();
                long time = threadMXBean.getThreadCpuTime(id); // zwraca -1 jak thread martwy bo jest GUPIE
                times.add(time);
            }
            times.add(Collections.max(times));  // hack na uzupełnienie -1 z zabitego wątku, +- działa

            long total = times.stream().mapToLong(Long::longValue).sum();
            System.out.printf("> %-30s%s ns\n", "Total cpu time:", deltaToString(total));
        }

        // Mierzymy czas rzeczywisty


        // Mordujemy pozostałe
        for (Thread thread : _threads) {
            thread.stop();
        }
    }


    public void joinAny() {
        Thread joiner = new Thread(this::awaitJoinAny);
        joiner.start();
    }
}

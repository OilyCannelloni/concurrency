package org.example;

public class Lab1_RaceCondition {
    public static void main() {
        int nThreads = 100;
        int nLoops = 2000;

        IntValue value = new IntValue();
        Thread[] threads = new Thread[2 * nThreads];
        for (int i = 0; i < 2*nThreads; i++) {
            if (i % 2 == 0)
                threads[i] = new IncrementingThread(value, nLoops);
            else
                threads[i] = new DecrementingThread(value, nLoops);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }

        System.out.printf("IntValue: " + value.get());
    }
}

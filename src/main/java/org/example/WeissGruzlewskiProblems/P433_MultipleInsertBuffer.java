package org.example.WeissGruzlewskiProblems;

import org.example.*;

public class P433_MultipleInsertBuffer {
    public static void main() {
        int maxInsert = 10;
        int nProducers = 10, nConsumers = 5;
        MultipleInsertBuffer<Integer> buffer = new MultipleInsertBuffer<>(maxInsert);

        Thread[] threads = new Thread[nProducers + nConsumers];
        for (int i = 0; i < nProducers; i++) {
            threads[i] = new MultipleProducer<>(i, buffer, maxInsert, 1, 100, 0);
        }
        for (int i = nProducers; i < nProducers + nConsumers; i++) {
            threads[i] = new MultipleConsumer<>(i, buffer, maxInsert, 100, 0);
        }

        for (Thread t : threads)
            t.start();


        try {
            for (Thread t : threads)
                t.join();
            System.out.println("All threads joined");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

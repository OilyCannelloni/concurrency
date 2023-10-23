package org.example.WeissGruzlewskiProblems;

import org.example.Buffer;
import org.example.Consumer;
import org.example.Producer;
import org.example.WaitNotifyBuffer;

public class P422_ProducerConsumer {
    public static void main() {
        Buffer<Integer> buffer = new WaitNotifyBuffer<>(10);
        int nProducers = 100;
        int nConsumers = 100;

        Thread[] threads = new Thread[nProducers + nConsumers];
        for (int i = 0; i < nProducers; i++) {
            Producer<Integer> producer = new Producer<>(i, buffer, 1, 10, 0);
            threads[i] = producer;
        }
        for (int i = 0; i < nConsumers; i++) {
            Consumer<Integer> consumer = new Consumer<>(i, buffer, 10, 0);
            threads[nProducers + i] = consumer;
        }

        for (Thread thread : threads)
            thread.start();



        try {
            for (Thread thread : threads)
                thread.join();
            System.out.println("All threads joined.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

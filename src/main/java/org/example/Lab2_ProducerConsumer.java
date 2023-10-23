package org.example;

public class Lab2_ProducerConsumer {
    public static void main() {
        /*
        Przychodzi P1. Wchodzi do M, bufor ok, wstawiamy 1, (***) wysyłamy Notify, wysłane w powietrze
        (***) Przychodzi C1. Konsumuje, wychodzi.
        Przychodzi P2. Wchodzi do M, bufor pełny, czekamy na wait()
        Przychodzi P1. Wchodzi do M, wisi na wait()

         */



        Buffer<Integer> buffer = new WaitNotifyBuffer<>(1);
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

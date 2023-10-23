package org.example;

public class Lab2_ProducerConsumer {
    public static void main() {
        /*
        //  2 producentów 1 konsument, układ zdarzeń prowadzący do zakleszczenia:
        //   0. init B=0
        //   1. producent 1 wpisuje wartość i wychodzi B=1
        //   2. producent 2 wchodzi do synchronized i wisi B=1
        //   3. producent 1 wchodzi do synchronized i wisi B=1
        //   4. konsument konsumuje, notyfikuje i wychodzi B=0
        //   5. producent 1 jest notyfikowany, próbuje uzyskać dostęp
        //   6. ...ale scheduler przyznaje dostęp konsumentowi.
        //   7. konsument wchodzi do synchronized i wisi B=0
        //   8. producent 1 wpisuje wartość, notyfikuje i wychodzi B=1
        //   9. producent 2 jest notyfikowany, wchodzi do synchronized i wisi B=1
        //   10. producent 1 wchodzi do synchronized i wisi B=1
        //       - wszystko wisi, mamy ZAKLESZCZENIE -
        */


        Buffer buffer = new WaitNotifyBuffer(1);
        int nProducers = 100;
        int nConsumers = 100;

        Thread[] threads = new Thread[nProducers + nConsumers];
        for (int i = 0; i < nProducers; i++) {
            Producer producer = new Producer(i, buffer, 10, 0);
            threads[i] = producer;
        }
        for (int i = 0; i < nConsumers; i++) {
            Consumer consumer = new Consumer(i, buffer, 10, 0);
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

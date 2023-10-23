package org.example.lab;

import org.example.containers.Buffer;
import org.example.containers.WaitNotifyBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.Consumer;
import org.example.threads.Producer;

import java.util.Map;

public class Lab2_ProducerConsumer {
    static int N_PRODUCERS = 100, N_CONSUMERS = 100;

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

        ThreadFactory producer = new ThreadFactory(Producer.class).setBuffer(buffer);
        ThreadFactory consumer = new ThreadFactory(Consumer.class).setBuffer(buffer);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, N_PRODUCERS,
                consumer, N_CONSUMERS
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

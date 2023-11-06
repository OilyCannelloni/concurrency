package org.example.lab;

import org.example.containers.FourCond_Boolean_MultipleBuffer;
import org.example.containers.FourCond_HasWaiters_MultipleBuffer;
import org.example.containers.IMultipleBuffer;
import org.example.containers.TwoCond_MultipleBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class Zadanie1 {
    static int MAX_INSERT = 5, BIG_CONSUMER_N_ITEMS = 5;
    public static void main() {
        IMultipleBuffer buffer = new TwoCond_MultipleBuffer(2 * MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);
        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT - 2);
        ThreadFactory bigConsumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(BIG_CONSUMER_N_ITEMS)
                .setRandom(false)
                .setInitialID(1000);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 1,
                consumer, 1,
                bigConsumer, 1
        ));

        threadRunner.startAll();
        threadRunner.joinAny();
    }
}

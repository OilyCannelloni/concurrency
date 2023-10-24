package org.example.WeissGruzlewskiProblems;

import org.example.containers.IMultipleBuffer;
import org.example.containers.NonStarvingMultipleBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class P443_NonStarvingMultiplePC {
    static int MAX_INSERT = 10, BIG_CONSUMER_N_ITEMS = 10;
    public static void main() {
        IMultipleBuffer buffer = new NonStarvingMultipleBuffer(2 * MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);
        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);
        ThreadFactory bigConsumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(BIG_CONSUMER_N_ITEMS)
                .setRandom(false)
                .setInitialID(1000);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 2,
                consumer, 2,
                bigConsumer, 1
        ));
        threadRunner.startAll();
        threadRunner.joinAll();
    }

}

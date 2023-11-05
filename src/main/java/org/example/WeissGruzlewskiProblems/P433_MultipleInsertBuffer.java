package org.example.WeissGruzlewskiProblems;

import org.example.containers.IMultipleBuffer;
import org.example.containers.TwoCond_MultipleBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class P433_MultipleInsertBuffer {
    static int MAX_INSERT = 10;

    public static void main() {
        IMultipleBuffer buffer = new TwoCond_MultipleBuffer(MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadFactory bigConsumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT)
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

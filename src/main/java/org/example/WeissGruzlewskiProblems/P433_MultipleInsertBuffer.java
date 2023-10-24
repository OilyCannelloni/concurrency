package org.example.WeissGruzlewskiProblems;

import org.example.containers.IMultipleBuffer;
import org.example.containers.MultipleInsertBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class P433_MultipleInsertBuffer {
    static int MAX_INSERT = 10;

    public static void main() {
        IMultipleBuffer buffer = new MultipleInsertBuffer(MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 3,
                consumer, 5
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }


}

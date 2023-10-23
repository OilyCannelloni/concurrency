package org.example.lab;

import org.example.containers.MultipleInsertBuffer;
import org.example.meta.*;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class Lab3_LockCondition {
    static int MAX_INSERT = 10;

    public static void main() {
        MultipleInsertBuffer buffer = new MultipleInsertBuffer(MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMIBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMIBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadFactory maxConsumer = new ThreadFactory(MultipleConsumer.class)
                .setMIBuffer(buffer)
                .setElementCount(MAX_INSERT)
                .setRandom(false)
                .setInitialID(99);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 3,
                consumer, 5,
                maxConsumer, 1
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

package org.example.lab;

import org.example.containers.IMultipleBuffer;
import org.example.containers.NestedLockBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class Lab5_NestedLocks {
    static int MAX_INSERT = 10;
    public static void main() {
        System.out.println("Nested Locks Producer-Consumer");
        IMultipleBuffer buffer = new NestedLockBuffer(2 * MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);
        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer)
                .setElementCount(MAX_INSERT);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 30,
                consumer, 30
        ));

        threadRunner.startAll();
        threadRunner.joinAny();
    }
}

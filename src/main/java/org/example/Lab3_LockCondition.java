package org.example;

import org.example.meta.*;

import java.util.Map;

public class Lab3_LockCondition {
    public static void main() {
        int maxInsert = 10;

        MultipleInsertBuffer buffer = new MultipleInsertBuffer(maxInsert);
        MultipleProducerFactory producer = new MultipleProducerFactory(
                buffer, maxInsert, 100, 0, 0, 0);
        MultipleConsumerFactory consumer = new MultipleConsumerFactory(
                buffer, maxInsert, 100, 0, 0, 0);
        MultipleConsumerFactory maxConsumer = new MultipleConsumerFactory(
                buffer, maxInsert, 100, 0, 10, 99);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 3,
                consumer, 5,
                maxConsumer, 1
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

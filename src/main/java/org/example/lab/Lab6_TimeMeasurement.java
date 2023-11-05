package org.example.lab;

import org.example.containers.FourCond_Boolean_MultipleBuffer;
import org.example.containers.IMultipleBuffer;
import org.example.containers.TwoCond_MultipleBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.MultipleConsumer;
import org.example.threads.MultipleProducer;

import java.util.Map;

public class Lab6_TimeMeasurement {
    static int MAX_INSERT = 5;
    public static void main() {
        IMultipleBuffer buffer_4 = new FourCond_Boolean_MultipleBuffer(2 * MAX_INSERT);

        ThreadFactory producer = new ThreadFactory(MultipleProducer.class)
                .setMultipleBuffer(buffer_4)
                .setElementCount(MAX_INSERT)
                .setVerbose(false);

        ThreadFactory consumer = new ThreadFactory(MultipleConsumer.class)
                .setMultipleBuffer(buffer_4)
                .setElementCount(MAX_INSERT)
                .setVerbose(false);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 5,
                consumer, 5
        ));
        threadRunner.setMeasureTime(true);
        threadRunner.startAll();
        threadRunner.joinAny();


        IMultipleBuffer buffer_2 = new TwoCond_MultipleBuffer(2 * MAX_INSERT);
        producer.setMultipleBuffer(buffer_2);
        consumer.setMultipleBuffer(buffer_2);
        threadRunner = new ThreadRunner(Map.of(
                producer, 5,
                consumer, 5
        ));
        threadRunner.setMeasureTime(true);
        threadRunner.startAll();
        threadRunner.joinAny();
    }
}

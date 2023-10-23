package org.example.WeissGruzlewskiProblems;

import org.example.*;
import org.example.meta.ConsumerFactory;
import org.example.meta.ProducerFactory;
import org.example.meta.ThreadRunner;

import java.util.Map;

public class P422_ProducerConsumer {
    public static void main() {
        Buffer buffer = new LockConditionBuffer(10);
        ProducerFactory producerF = new ProducerFactory(buffer, 100, 0);
        ConsumerFactory consumerF = new ConsumerFactory(buffer, 100, 0);
        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producerF, 7,
                consumerF, 5
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

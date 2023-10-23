package org.example.WeissGruzlewskiProblems;

import org.example.containers.Buffer;
import org.example.containers.LockConditionBuffer;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.Consumer;
import org.example.threads.Producer;

import java.util.Map;

public class P422_ProducerConsumer {
    public static void main() {
        Buffer buffer = new LockConditionBuffer(10);

        ThreadFactory producer = new ThreadFactory(Producer.class).setBuffer(buffer);
        ThreadFactory consumer = new ThreadFactory(Consumer.class).setBuffer(buffer);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                producer, 5,
                consumer, 5
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

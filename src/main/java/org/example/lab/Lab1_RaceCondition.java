package org.example.lab;

import org.example.containers.IntValue;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;
import org.example.threads.Decrementer;
import org.example.threads.Incrementer;

import java.util.Map;

public class Lab1_RaceCondition {
    static int N_THREADS = 100, N_LOOPS = 1000;
    public static void main() {
        IntValue value = new IntValue(0);
        ThreadFactory incrementer = new ThreadFactory(Incrementer.class).setValue(value).setLoopCount(N_LOOPS);
        ThreadFactory decrementer = new ThreadFactory(Decrementer.class).setValue(value).setLoopCount(N_LOOPS);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                incrementer, N_THREADS,
                decrementer, N_THREADS
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
        System.out.println(value.get());
    }
}

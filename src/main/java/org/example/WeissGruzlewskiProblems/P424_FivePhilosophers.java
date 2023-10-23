package org.example.WeissGruzlewskiProblems;

import org.example.threads.Philosopher;
import org.example.containers.SpaghettiTable;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;

import java.util.Map;

public class P424_FivePhilosophers {
    private final static int N_PEOPLE = 5;
    public static void main() {
        SpaghettiTable table = new SpaghettiTable(N_PEOPLE);
        ThreadFactory philosopher = new ThreadFactory(Philosopher.class).setSpaghettiTable(table);
        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                philosopher, N_PEOPLE
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

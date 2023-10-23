package org.example.WeissGruzlewskiProblems;

import org.example.SpaghettiTable;
import org.example.meta.PhilosopherFactory;
import org.example.meta.ThreadRunner;

import java.util.Map;

public class P424_FivePhilosophers {
    public static void main() {
        int nEaters = 5;
        SpaghettiTable table = new SpaghettiTable(nEaters);
        PhilosopherFactory philosopherFactory = new PhilosopherFactory(table);
        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                philosopherFactory, nEaters
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

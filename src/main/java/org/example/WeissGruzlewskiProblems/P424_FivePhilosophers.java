package org.example.WeissGruzlewskiProblems;

import org.example.Philosopher;
import org.example.SpaghettiTable;

public class P424_FivePhilosophers {
    public static void main() {
        System.out.println("aaaa");
        int nEaters = 5;
        SpaghettiTable table = new SpaghettiTable(nEaters);
        Thread[] threads = new Thread[nEaters];
        for (int i = 0; i < nEaters; i++) {
            threads[i] = new Philosopher(i, table, 10, 0, 0);
        }
        for (Thread t : threads)
            t.start();

        try {
            for (Thread t : threads)
                t.join();
            System.out.println("All threads joined");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example.WeissGruzlewskiProblems;

import org.example.IValue;
import org.example.IntReader;
import org.example.IntWriter;
import org.example.ReadWriteProtectedIntValue;

public class P423_ReaderWriter {
    public static void main() {
        int nReaders = 100;
        int nWriters = 50;
        IValue value = new ReadWriteProtectedIntValue();

        Thread[] threads = new Thread[nReaders + nWriters];
        for (int i = 0; i < nReaders; i++) {
            threads[i] = new IntReader(i, value, 100, 0);
        }
        for (int i = nReaders; i < nReaders + nWriters; i++) {
            threads[i] = new IntWriter(i, value, 100, 0);
        }

        for (Thread t : threads)
            t.start();

        try {
            for (Thread t : threads)
                t.join();
            System.out.println("All threads joined.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

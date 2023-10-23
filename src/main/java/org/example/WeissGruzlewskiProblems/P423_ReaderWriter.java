package org.example.WeissGruzlewskiProblems;

import org.example.IValue;
import org.example.ReadWriteProtectedIntValue;
import org.example.meta.ReaderFactory;
import org.example.meta.ThreadRunner;
import org.example.meta.WriterFactory;

import java.util.Map;

public class P423_ReaderWriter {
    public static void main() {
        int nReaders = 100;
        int nWriters = 50;
        IValue value = new ReadWriteProtectedIntValue();

        WriterFactory writerFactory = new WriterFactory(value, 100, 0);
        ReaderFactory readerFactory = new ReaderFactory(value, 100, 0);
        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                writerFactory, nWriters,
                readerFactory, nReaders
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

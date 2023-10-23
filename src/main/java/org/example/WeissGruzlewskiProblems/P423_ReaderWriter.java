package org.example.WeissGruzlewskiProblems;

import org.example.containers.IValue;
import org.example.threads.IntReader;
import org.example.threads.IntWriter;
import org.example.containers.ReadWriteProtectedIntValue;
import org.example.meta.ThreadFactory;
import org.example.meta.ThreadRunner;

import java.util.Map;

public class P423_ReaderWriter {
    public static void main() {
        IValue value = new ReadWriteProtectedIntValue();

        ThreadFactory writerFactory = new ThreadFactory(IntWriter.class).setValue(value);
        ThreadFactory readerFactory = new ThreadFactory(IntReader.class).setValue(value);

        ThreadRunner threadRunner = new ThreadRunner(Map.of(
                writerFactory, 100,
                readerFactory, 50
        ));

        threadRunner.startAll();
        threadRunner.joinAll();
    }
}

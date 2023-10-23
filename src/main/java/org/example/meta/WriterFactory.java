package org.example.meta;

import org.example.IValue;
import org.example.IntWriter;

public class WriterFactory implements IThreadFactory {
    private final IValue _value;
    private final int _nLoops, _sleep;
    private int _id;

    public WriterFactory(IValue value, int nLoops, int sleep) {
        _value = value;
        _id = -1;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    public IntWriter create() {
        return new IntWriter(++_id, _value, _nLoops, _sleep);
    }
}


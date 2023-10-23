package org.example.meta;

import org.example.IValue;
import org.example.IntReader;

public class ReaderFactory implements IThreadFactory {
    private final IValue _value;
    private final int _nLoops, _sleep;
    private int _id;

    public ReaderFactory(IValue value, int nLoops, int sleep) {
        _value = value;
        _id = -1;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    public IntReader create() {
        return new IntReader(++_id, _value, _nLoops, _sleep);
    }
}

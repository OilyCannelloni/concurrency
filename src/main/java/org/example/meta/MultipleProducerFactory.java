package org.example.meta;

import org.example.MultipleInsertBuffer;
import org.example.MultipleProducer;

public class MultipleProducerFactory implements IThreadFactory {
    private final MultipleInsertBuffer _buffer;
    private final int _sleep, _maxPut, _nLoops, _nProduced;
    private int _id = -1;

    public MultipleProducerFactory(MultipleInsertBuffer buffer, int maxPut, int nLoops, int sleep, int nProduced, int forceID) {
        _buffer = buffer;
        _maxPut = maxPut;
        _nLoops = nLoops;
        _sleep = sleep;
        _nProduced = nProduced;
        if (forceID != 0)
            _id = forceID - 1;
    }

    @Override
    public MultipleProducer create() {
        return new MultipleProducer(++_id, _buffer, _maxPut, _nLoops, _sleep, _nProduced);
    }
}

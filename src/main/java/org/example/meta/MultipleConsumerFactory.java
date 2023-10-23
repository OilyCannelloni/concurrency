package org.example.meta;

import org.example.MultipleConsumer;
import org.example.MultipleInsertBuffer;

public class MultipleConsumerFactory implements IThreadFactory {
    private final MultipleInsertBuffer _buffer;
    private final int _sleep, _maxPut, _nLoops, _nConsumed;
    private int _id = -1;

    public MultipleConsumerFactory(MultipleInsertBuffer buffer, int maxPut, int nLoops, int sleep, int nConsumed, int forceID) {
        _buffer = buffer;
        _maxPut = maxPut;
        _nLoops = nLoops;
        _sleep = sleep;
        _nConsumed = nConsumed;
        if (forceID != 0) {
            _id = forceID - 1;
        }
    }

    @Override
    public MultipleConsumer create() {
        return new MultipleConsumer(++_id, _buffer, _maxPut, _nLoops, _sleep, _nConsumed);
    }
}
package org.example.meta;

import org.example.Buffer;
import org.example.Producer;

public class ProducerFactory implements IThreadFactory {
    private final Buffer _buffer;
    private final int _nLoops, _sleep;
    private int _currentId = -1;

    public ProducerFactory(Buffer buffer, int nLoops, int sleep) {
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    public Producer create() {
        _currentId++;
        return new Producer(_currentId, _buffer, _nLoops, _sleep);
    }
}

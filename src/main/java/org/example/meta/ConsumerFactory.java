package org.example.meta;

import org.example.Buffer;
import org.example.Consumer;

public class ConsumerFactory implements IThreadFactory {
    private final Buffer _buffer;
    private final int _nLoops, _sleep;
    private int _currentId = -1;

    public ConsumerFactory(Buffer buffer, int nLoops, int sleep) {
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    public Consumer create() {
        _currentId++;
        return new Consumer(_currentId, _buffer, _nLoops, _sleep);
    }
}

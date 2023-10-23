package org.example.meta;

import org.example.containers.Buffer;
import org.example.containers.IValue;
import org.example.containers.MultipleInsertBuffer;
import org.example.containers.SpaghettiTable;
import org.example.threads.*;

public class ThreadFactory implements IThreadFactory {
    private final Class<? extends Thread> _cls;

    private IValue _value;
    private Buffer _buffer;
    private MultipleInsertBuffer _multipleInsertBuffer;
    private SpaghettiTable _table;
    private int _loopCount = 100, _sleep = 0, _currentID = -1, _elementCount = 1;
    private boolean _isRandom = true;

    public ThreadFactory(Class<? extends Thread> cls) {
        _cls = cls;
    }

    public ThreadFactory setBuffer(Buffer buffer) {
        _buffer = buffer;
        return this;
    }

    public ThreadFactory setMIBuffer(MultipleInsertBuffer miBuffer) {
        _multipleInsertBuffer = miBuffer;
        return this;
    }

    public ThreadFactory setLoopCount(int nLoops) {
        _loopCount = nLoops;
        return this;
    }

    public ThreadFactory setSleep(int sleepMillis) {
        _sleep = sleepMillis;
        return this;
    }

    public ThreadFactory setInitialID(int id) {
        _currentID = id - 1;
        return this;
    }

    public ThreadFactory setElementCount(int elementCount) {
        _elementCount = elementCount;
        return this;
    }

    public ThreadFactory setRandom(boolean isRandom) {
        _isRandom = isRandom;
        return this;
    }

    public ThreadFactory setSpaghettiTable(SpaghettiTable table) {
        _table = table;
        return this;
    }

    public ThreadFactory setValue(IValue value) {
        _value = value;
        return this;
    }


    @Override
    public Thread create() {
        if (_cls == Producer.class)
            return new Producer(++_currentID, _buffer, _loopCount, _sleep);
        else if (_cls == Consumer.class)
            return new Consumer(++_currentID, _buffer, _loopCount, _sleep);

        else if (_cls == MultipleProducer.class)
            return new MultipleProducer(++_currentID, _multipleInsertBuffer, _loopCount, _sleep, _elementCount, _isRandom);
        else if (_cls == MultipleConsumer.class)
            return new MultipleConsumer(++_currentID, _multipleInsertBuffer, _loopCount, _sleep, _elementCount, _isRandom);

        else if (_cls == Philosopher.class)
            return new Philosopher(++_currentID, _table, _loopCount, _sleep);

        else if (_cls == IntReader.class)
            return new IntReader(++_currentID, _value, _loopCount, _sleep);
        else if (_cls == IntWriter.class)
            return new IntWriter(++_currentID, _value, _loopCount, _sleep);

        else if (_cls == Decrementer.class)
            return new Decrementer(_value, _loopCount);
        else if (_cls == Incrementer.class)
            return new Incrementer(_value, _loopCount);

        throw new IllegalArgumentException("Invalid factory configuration");
    }
}

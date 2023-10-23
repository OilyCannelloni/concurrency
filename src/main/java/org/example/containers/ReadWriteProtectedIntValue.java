package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteProtectedIntValue implements IValue {
    private int _value = 0, _nReaders = 0, _nWriters = 0;

    private final Lock _lock = new ReentrantLock();
    private final Condition _noWriter = _lock.newCondition();
    private final Condition _noReader = _lock.newCondition();

    @Override
    public int get() throws InterruptedException {
        _lock.lock();
        while (_nWriters > 0)
            _noWriter.await();
        _nReaders++;

        int value = _value;

        _nReaders--;
        if (_nReaders == 0)
            _noReader.signal();
        _lock.unlock();
        return value;
    }

    @Override
    public void set(int v) throws InterruptedException {
        _lock.lock();
        while (_nReaders > 0 || _nWriters > 0)
            _noReader.await();
        _nWriters++;

        _value = v;

        _nWriters--;
        _noWriter.signalAll();
        _noReader.signal();
        _lock.unlock();
    }
}

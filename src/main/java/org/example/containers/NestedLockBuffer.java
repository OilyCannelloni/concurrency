package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NestedLockBuffer extends Buffer implements IMultipleBuffer {

    private final ReentrantLock _nextActionLock = new ReentrantLock();
    private final ReentrantLock _consumerLock = new ReentrantLock();
    private final ReentrantLock _producerLock = new ReentrantLock();
    private final Condition _commonCondition = _nextActionLock.newCondition();

    public NestedLockBuffer(int length) {
        super(length);
    }

    @Override
    public void put(int[] elements) throws InterruptedException {
        _producerLock.lock();
        _nextActionLock.lock();
        while (_nItems + elements.length > _length) {
            _commonCondition.await();
        }

        for (int e : elements)
            super.put(e);

        _commonCondition.signal();
        _nextActionLock.unlock();
        _producerLock.unlock();
    }

    @Override
    public int[] take(int n) throws InterruptedException {
        _consumerLock.lock();
        _nextActionLock.lock();
        while (_nItems < n) {
            _commonCondition.await();
        }

        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = super.take();

        _commonCondition.signal();
        _nextActionLock.unlock();
        _consumerLock.unlock();
        return ret;
    }
}
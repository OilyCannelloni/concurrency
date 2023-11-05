package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TwoCond_MultipleBuffer extends Buffer implements IMultipleBuffer {
    private final Lock _lock = new ReentrantLock();
    private final Condition _taken = _lock.newCondition();
    private final Condition _added = _lock.newCondition();

    public TwoCond_MultipleBuffer(int length) {
        super(2 * length);
    }

    public void put(int[] elements) throws InterruptedException {
        _lock.lock();
        while (_nItems + elements.length > _length) {
            _taken.await();
        }

        for (int e : elements)
            super.put(e);

        _added.signal();
        _lock.unlock();
    }

    public int[] take(int n) throws InterruptedException {
        _lock.lock();
        while (_nItems < n) {
            _added.await();
        }

        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = super.take();


        _taken.signal();
        _lock.unlock();
        return ret;
    }
}

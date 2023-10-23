package org.example;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleInsertBuffer extends Buffer {
    private final int _maxInsert;
    private final Lock _lock = new ReentrantLock();
    private final Condition _taken = _lock.newCondition();
    private final Condition _added = _lock.newCondition();

    public MultipleInsertBuffer(int maxInsert) {
        super(2 * maxInsert);
        _maxInsert = maxInsert;
    }

    public void put(int[] elements) throws InterruptedException {
        if (elements.length > _maxInsert) return;
        _lock.lock();
        while (_nItems + elements.length > _length) {
            _taken.await();
        }

        for (int e : elements)
            super.put(e);

        _added.signalAll();
        _taken.signalAll();
        _lock.unlock();
    }

    public int[] take(int n) throws InterruptedException {
        if (n > _maxInsert) return null;
        _lock.lock();
        while (_nItems < n) {
            _added.await();
        }

        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = super.take();


        _taken.signalAll();
        _added.signalAll();
        _lock.unlock();
        return ret;
    }
}

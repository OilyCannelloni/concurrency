package org.example.containers;

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

        _added.signal();
        _lock.unlock();
    }

    public int[] take(int nTake) throws InterruptedException {
        if (nTake > _maxInsert) return null;
        _lock.lock();
        while (_nItems < nTake) {
            _added.await();
        }

        int[] ret = new int[nTake];
        for (int i = 0; i < nTake; i++)
            ret[i] = super.take();


        _taken.signal();
        _lock.unlock();
        return ret;
    }
}

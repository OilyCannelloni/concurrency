package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionBuffer extends Buffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public LockConditionBuffer(int length) {
        super(length);
    }

    @Override
    public void put(int item) throws InterruptedException {
        lock.lock();
        try {
            while (_nItems >= _length) {
                notFull.await();
            }

            super.put(item);

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int take() throws InterruptedException {
        lock.lock();
        try {
            while (_nItems == 0) {
                notEmpty.await();
            }

            int item = super.take();

            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}

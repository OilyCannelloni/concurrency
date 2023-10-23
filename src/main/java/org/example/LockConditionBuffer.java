package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionBuffer<T> extends Buffer<T> {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    @Override
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (_nItems >= _length) {
                notFull.await();
            }
            _buffer.add(item);
            _nItems++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (_nItems == 0) {
                notEmpty.await();
            }
            T item = _buffer.remove(0);
            _nItems--;
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}

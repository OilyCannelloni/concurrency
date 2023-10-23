package org.example;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleInsertBuffer<T> extends Buffer<T> {
    private final int _maxInsert;
    private final Lock _lock = new ReentrantLock();
    private final Condition _taken = _lock.newCondition();
    private final Condition _added = _lock.newCondition();

    public MultipleInsertBuffer(int maxInsert) {
        super(2 * maxInsert);
        _maxInsert = maxInsert;
    }

    @Override
    public void put(T item) {
        _buffer.add(item);
    }

    @Override
    public T take() {
        return _buffer.remove(0);
    }

    public void put(Collection<T> elements) throws InterruptedException {
        _lock.lock();
        while (_nItems + elements.size() > _length) {
            _taken.await();
        }

        for (T e : elements)
            put(e);
        _nItems += elements.size();

        _added.signalAll();
        _taken.signalAll();
        _lock.unlock();
    }

    public Collection<T> take(int n) throws InterruptedException {
        _lock.lock();
        while (_nItems < n) {
            _added.await();
        }

        LinkedList<T> ret = new LinkedList<>();
        for (int i = 0; i < n; i++)
            ret.add(take());
        _nItems -= n;


        _taken.signalAll();
        _added.signalAll();
        _lock.unlock();
        return ret;
    }
}

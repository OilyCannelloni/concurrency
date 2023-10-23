package org.example;

public class WaitNotifyBuffer<T> extends Buffer<T> {
    public WaitNotifyBuffer(int length) {
        super(length);
    }

    public synchronized void put(T item) throws InterruptedException {
        while (_nItems >= _length) {
            wait();
        }
        _buffer.add(item);
        _nItems++;
        notifyAll();
    }

    public synchronized T take() throws InterruptedException {
        while (_nItems == 0) {
            wait();
        }
        T item = _buffer.remove(0);
        _nItems--;
        notifyAll();
        return item;
    }
}

package org.example.containers;

public class WaitNotifyBuffer extends Buffer {
    public WaitNotifyBuffer(int length) {
        super(length);
    }

    public synchronized void put(int item) throws InterruptedException {
        while (_nItems >= _length) {
            wait();
        }

        super.put(item);

        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        while (_nItems == 0) {
            wait();
        }

        int item = super.take();

        notifyAll();
        return item;
    }
}

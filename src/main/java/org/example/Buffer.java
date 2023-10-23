package org.example;

import java.util.LinkedList;
import java.util.List;

public abstract class Buffer<T> {
    protected final int _length;
    protected int _nItems;
    protected final List<T> _buffer = new LinkedList<>();

    public Buffer() {
        _length = 1;
    }

    public Buffer(int length) {
        _length = length;
    }

    public abstract void put(T item) throws InterruptedException;
    public abstract T take() throws InterruptedException;

    public int getItemCount() {
        return _nItems;
    }
}

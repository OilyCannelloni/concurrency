package org.example;


public abstract class Buffer {
    protected final int _length;
    protected int _nItems, _takePtr, _putPtr;
    protected final int[] _buffer;

    public Buffer(int length) {
        _length = length;
        _buffer = new int[length];
    }

    public void put(int item) throws InterruptedException {
        _buffer[_putPtr] = item;
        _putPtr = (_putPtr + 1) % _length;
        _nItems++;
    }

    public int take() throws InterruptedException {
        int e = _buffer[_takePtr++];
        _takePtr = (_takePtr + 1) % _length;
        _nItems--;
        return e;
    }

    public int getItemCount() {
        return _nItems;
    }
}

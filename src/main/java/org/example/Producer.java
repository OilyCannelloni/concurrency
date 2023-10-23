package org.example;

public class Producer<T> extends Thread {
    private final T _producedData;
    private final Buffer<T> _buffer;
    private final int _nLoops;
    private final int _sleep;
    private final int _id;

    public Producer(int id, Buffer<T> buffer, T producedData, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _producedData = producedData;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                _buffer.put(_producedData);
                System.out.println("ID: " + _id + " Produced: " + _producedData + "   Item count: " + _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

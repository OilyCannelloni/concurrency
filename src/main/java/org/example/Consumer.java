package org.example;

public class Consumer<T> extends Thread {
    private final Buffer<T> _buffer;
    private final int _nLoops;
    private final int _sleep, _id;

    public Consumer(int id, Buffer<T> buffer, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                T _consumedData = _buffer.take();
                System.out.println("ID: " + _id +" Consumed: " + _consumedData + "   Item count: " + _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

package org.example;

public class Consumer extends Thread {
    private final Buffer _buffer;
    private final int _nLoops, _sleep, _id;

    public Consumer(int id, Buffer buffer, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int consumed = _buffer.take();
                System.out.printf("Consumer %d Consumed: %d Item count: %d\n", _id, consumed, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

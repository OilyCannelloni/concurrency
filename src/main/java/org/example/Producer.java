package org.example;

public class Producer extends Thread {
    private final Buffer _buffer;
    private final int _nLoops, _sleep, _id;

    public Producer(int id, Buffer buffer, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                _buffer.put(_id);
                System.out.printf("Producer %d Produced: %d Item count: %d\n", _id, _id, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

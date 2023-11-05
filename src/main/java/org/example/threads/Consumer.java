package org.example.threads;

import org.example.containers.Buffer;

public class Consumer extends Thread {
    private final Buffer _buffer;
    private final int _nLoops, _sleep, _id;
    private boolean _verbose;

    public Consumer(int id, Buffer buffer, int nLoops, int sleep, boolean verbose) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
        _verbose = verbose;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int consumed = _buffer.take();
                if (_verbose)
                    System.out.printf("Consumer %d Consumed: %d Item count: %d\n", _id, consumed, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

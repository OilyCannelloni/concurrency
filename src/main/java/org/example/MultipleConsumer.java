package org.example;

import java.util.Random;

public class MultipleConsumer extends Thread {
    private final MultipleInsertBuffer _buffer;
    private final int _nLoops;
    private final int _sleep, _id, _maxTake, _nConsumed;
    private final Random rand = new Random();

    public MultipleConsumer(int id, MultipleInsertBuffer buffer, int maxTake, int nLoops, int sleep, int nConsumed) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
        _maxTake = maxTake;
        _nConsumed = nConsumed;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int n = _nConsumed;
                if (_nConsumed == 0)
                    n = rand.nextInt(_maxTake);

                int[] _consumedData = _buffer.take(n);
                System.out.printf("ID: C%d nConsumed: %d nElements: %d\n", _id, n, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

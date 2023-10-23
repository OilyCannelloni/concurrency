package org.example.threads;

import org.example.containers.MultipleInsertBuffer;

import java.util.Random;

public class MultipleProducer extends Thread {
    private final MultipleInsertBuffer _buffer;
    private final int _nLoops;
    private final int _sleep, _nProduced;
    private final int _id;
    private final Random rand = new Random();
    private final boolean _isRandom;

    public MultipleProducer(int id, MultipleInsertBuffer buffer, int nLoops, int sleep, int nProduced, boolean isRandom) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
        _nProduced = nProduced;
        _isRandom = isRandom;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int n = _nProduced;
                if (_isRandom)
                    n = rand.nextInt(_nProduced);
                int[] data = new int[n];

                for (int q = 0; q < n; q++) {
                    data[q] = _id;
                }

                _buffer.put(data);

                System.out.printf("ID: P%d nProduced: %d nElements: %d\n", _id, n, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

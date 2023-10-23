package org.example;

import java.util.Random;

public class MultipleProducer extends Thread {
    private final MultipleInsertBuffer _buffer;
    private final int _nLoops;
    private final int _sleep, _maxPut;
    private final int _id;
    private final Random rand = new Random();

    public MultipleProducer(int id, MultipleInsertBuffer buffer, int maxPut, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
        _maxPut = maxPut;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int n = rand.nextInt(_maxPut);
                int[] data = new int[n];
                for (int q = 0; q < n; q++) {
                    data[q] = _id;
                }

                _buffer.put(data);

                System.out.printf("ID: %d nProduced: %d nElements: %d\n", _id, n, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package org.example;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class MultipleProducer<T> extends Thread {
    private final T _producedData;
    private final MultipleInsertBuffer<T> _buffer;
    private final int _nLoops;
    private final int _sleep, _maxPut;
    private final int _id;
    private final Random rand = new Random();

    public MultipleProducer(int id, MultipleInsertBuffer<T> buffer, int maxPut, T producedData, int nLoops, int sleep) {
        _id = id;
        _buffer = buffer;
        _producedData = producedData;
        _nLoops = nLoops;
        _sleep = sleep;
        _maxPut = maxPut;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int n = rand.nextInt(_maxPut);
                LinkedList<T> list = new LinkedList<>();
                for (int q = 0; q < n; q++) {
                    list.add(_producedData);
                }

                _buffer.put(list);

                System.out.printf("ID: %d Produced: %d\n", _id, n);
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

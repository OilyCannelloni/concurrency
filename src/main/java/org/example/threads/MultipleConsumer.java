package org.example.threads;

import org.example.containers.IMultipleBuffer;
import org.example.meta.Common;

import java.util.Random;

public class MultipleConsumer extends Thread {
    private final IMultipleBuffer _buffer;
    private final int _nLoops;
    private final int _sleep, _id, _nConsumed;
    private final boolean _isRandom, _verbose;

    public MultipleConsumer(int id, IMultipleBuffer buffer, int nLoops, int sleep, int nConsumed, boolean isRandom, boolean verbose) {
        _id = id;
        _buffer = buffer;
        _nLoops = nLoops;
        _sleep = sleep;
        _nConsumed = nConsumed;
        _isRandom = isRandom;
        _verbose = verbose;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int n = _nConsumed;
                if (_isRandom)
                    n = Common.random.nextInt(_nConsumed - 1) + 1;

                int[] _consumedData = _buffer.take(n);

                if (_verbose)
                    System.out.printf("C%d nConsumed: %d nElements: %d\n", _id, n, _buffer.getItemCount());
                sleep(_sleep);
            } catch (InterruptedException e) {
                if (_verbose)
                    System.out.printf("C%d interrupted.\n", _id);
            }
        }
    }
}

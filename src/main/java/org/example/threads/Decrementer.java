package org.example.threads;

import org.example.containers.IValue;

public class Decrementer extends Thread {
    private final IValue _value;
    private final int _nLoops;

    public Decrementer(IValue v, int nLoops) {
        _value = v;
        _nLoops = nLoops;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                _value.set(_value.get() - 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package org.example;

public class DecrementingThread extends Thread {
    private final IntValue _value;
    private final int _nLoops;

    public DecrementingThread(IntValue v, int nLoops) {
        _value = v;
        _nLoops = nLoops;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            _value.decrement();
        }
    }
}

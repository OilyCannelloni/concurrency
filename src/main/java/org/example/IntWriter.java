package org.example;

public class IntWriter extends Thread {
    private final IValue _value;
    private final int _nLoops;
    private final int _sleep, _id;

    public IntWriter(int id, IValue value, int nLoops, int sleep) {
        _id = id;
        _value = value;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                _value.set(_id);
                System.out.println("ID: " + _id +" Write: " + _id);
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
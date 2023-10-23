package org.example;

public class IntReader extends Thread {
    private final IValue _value;
    private final int _nLoops;
    private final int _sleep, _id;

    public IntReader(int id, IValue value, int nLoops, int sleep) {
        _id = id;
        _value = value;
        _nLoops = nLoops;
        _sleep = sleep;
    }

    @Override
    public void run() {
        for (int i = 0; i < _nLoops; i++) {
            try {
                int v = _value.get();
                System.out.println("ID: " + _id +" Read: " + v);
                sleep(_sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

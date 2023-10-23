package org.example.meta;

import org.example.Philosopher;
import org.example.SpaghettiTable;

public class PhilosopherFactory implements IThreadFactory {
    private final SpaghettiTable _table;
    private final int _nLoops, _sleepTime, _eatTime;
    private int _id = -1;


    public PhilosopherFactory(SpaghettiTable table) {
        this(table, 100, 0, 0);
    }

    public PhilosopherFactory(SpaghettiTable table, int nLoops, int sleepTime, int eatTime) {
        _table = table;
        _nLoops = nLoops;
        _sleepTime = sleepTime;
        _eatTime = eatTime;
    }

    public Philosopher create() {
        return new Philosopher(++_id, _table, _nLoops, _sleepTime, _eatTime);
    }
}

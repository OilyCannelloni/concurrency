package org.example;

import java.util.Random;

public class Philosopher extends Thread {
    private final SpaghettiTable _table;
    private final int _id, _nLoops, _sleepTime, _eatTime;
    private final Random rand = new Random();

    public Philosopher(int id, SpaghettiTable table, int nLoops, int sleepTime, int eatTime) {
        _id = id;
        _table = table;
        _nLoops = nLoops;
        _sleepTime = sleepTime;
        _eatTime = eatTime;
    }

    private void _sleep() throws InterruptedException {
        if (_sleepTime == 0) return;
        sleep(rand.nextInt(_sleepTime));
    }

    private void _eat() throws InterruptedException {
        if (_eatTime == 0) return;
        sleep(rand.nextInt(_eatTime));
    }

    public void run() {
        try {
            for (int i = 0; i < _nLoops; i++) {
                _sleep();
                _table.take(_id);
                System.out.printf("ID: %d Eating\n", _id);
                _eat();
                _table.give(_id);
                System.out.printf("ID: %d Sleeping\n", _id);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

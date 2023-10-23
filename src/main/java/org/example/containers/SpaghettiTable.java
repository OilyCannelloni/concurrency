package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpaghettiTable {
    private final int _nPeople;
    private final int[] _nFreeForks;
    private final Lock _lock = new ReentrantLock();
    private final Condition[] _canEat;

    public SpaghettiTable(int nPeople) {
        _nPeople = nPeople;
        _nFreeForks = new int[nPeople];
        _canEat = new Condition[nPeople];
        for (int i = 0; i < nPeople; i++) {
            _canEat[i] = _lock.newCondition();
            _nFreeForks[i] = 2;
        }

    }

    private int right(int i) {
        return (i + 1) % _nPeople;
    }

    private int left(int i) {
        return (i + _nPeople - 1) % _nPeople;
    }

    public void take(int i) throws InterruptedException {
        int r = right(i), l = left(i);
        _lock.lock();

        while (_nFreeForks[i] < 2) {
            _canEat[i].await();
        }
        _nFreeForks[r]--;
        _nFreeForks[l]--;

        _lock.unlock();
    }

    public void give(int i) throws InterruptedException {
        int r = right(i), l = left(i);
        _lock.lock();

        _nFreeForks[r]++;
        _nFreeForks[l]++;
        if (_nFreeForks[r] == 2)
            _canEat[r].signal();
        if (_nFreeForks[l] == 2)
            _canEat[l].signal();

        _lock.unlock();
    }
}

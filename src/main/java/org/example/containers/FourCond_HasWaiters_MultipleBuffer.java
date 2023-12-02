package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
Zagłodzenie przy użyciu hasWaiters()
Bufor 10, max porcja 5

Konfiguracja początkowa:  W buforze mamy 0 elementów. C5 czeka na firstConsumer.
1.  Producent produkuje 4, udaje się. W buforze 4.
    Sygnalizuje firstConsumer.
    C5 wychodzi z kolejki warunku firstConsumer, wpada do oczekujących na locka, ale
        locka zajmuje nowy C4!

2.  C4 sprawdza firstProducer.hasWaiters(), lecz dostaje (zgodnie z prawdą) False.
    Przechodzi dalej i sprawdza, czy w buforze jest wystarczająco (jest 4)
    Konsumuje 4, w buforze 0

3.  Mamy stan z punktu 1, a wątek C5 się nie ruszył. Mamy zagłodzenie C5.
*/


/*
Deadlock przy użyciu hasWaiters()
Bufor 20, max porcja 10
PX = producent produkujący X

Konfiguracja startowa:  W buforze 0. C5 czeka na firstConsumer.
1.  P1 produkuje 1. W buforze 1.
    Sygnalizuje firstConsumer.
    C5 wychodzi z warunku firstConsumer, ale czeka na locku, bo wszedł C2.
    C2a sprawdza firstConsumer.hasWaiters(), dostaje FALSE!
    Nie może skonsumować, więc czeka na firstConsumer.
    Przychodzi C2b i dzieje się to samo.
    C5 dostaje locka i wraca na firstConsumer. Teraz mamy 3 wątki (C2, C2 i C5) na firstConsumer.

2.  P5 produkuje 5 (B = 6). Sygnalizuje firstConsumer.
    Sygnał dostaje C2 (zjada 2). B = 4
    P5 produkuje 5 (B = 9)
    Sygnał dostaje C2 (zjada 2). B = 7

3.  P5 chce produkować 5, ale nie może, bo pełny bufor i wisi.
    C5 czeka na firstConsumer, ale nie dostaje sygnału i wisi.
    C2a i C2b czekają na otherConsumers i wiszą
*/



public class FourCond_HasWaiters_MultipleBuffer extends Buffer implements IMultipleBuffer {

    private final ReentrantLock _lock = new ReentrantLock();
    private final Condition _firstProducer = _lock.newCondition();
    private final Condition _otherProducers = _lock.newCondition();
    private final Condition _firstConsumer = _lock.newCondition();
    private final Condition _otherConsumers = _lock.newCondition();

    public FourCond_HasWaiters_MultipleBuffer(int length) {
        super(length);
    }

    @Override
    public void put(int[] elements) throws InterruptedException {
        _lock.lock();
        while (_lock.hasWaiters(_firstProducer)) {
            _otherProducers.await();
        }
        while (_nItems + elements.length > _length) {
            _firstProducer.await();
        }

        for (int e : elements)
            super.put(e);

        _otherProducers.signal();
        _firstConsumer.signal();
        _lock.unlock();
    }

    @Override
    public int[] take(int n) throws InterruptedException {
        _lock.lock();
        while (_lock.hasWaiters(_firstConsumer)) {
            _otherConsumers.await();
        }
        while (_nItems < n) {
            _firstConsumer.await();
        }

        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = super.take();

        _otherConsumers.signal();
        _firstProducer.signal();
        _lock.unlock();
        return ret;
    }
}
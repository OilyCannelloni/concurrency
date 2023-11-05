package org.example.containers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FourCond_Boolean_MultipleBuffer extends Buffer implements IMultipleBuffer {
    private final ReentrantLock _lock = new ReentrantLock();
    private final Condition _firstProducer = _lock.newCondition();
    private final Condition _otherProducers = _lock.newCondition();
    private final Condition _firstConsumer = _lock.newCondition();
    private final Condition _otherConsumers = _lock.newCondition();
    private boolean _isFirstProducerWaiting = false, _isFirstConsumerWaiting = false;

    public FourCond_Boolean_MultipleBuffer(int length) {
        super(length);
    }

    @Override
    public void put(int[] elements) throws InterruptedException {
        _lock.lock();
        while (_isFirstProducerWaiting) {
            _otherProducers.await();
        }
        while (_nItems + elements.length > _length) {
            _isFirstProducerWaiting = true;
            _firstProducer.await();
        }
        _isFirstProducerWaiting = false;

        for (int e : elements)
            super.put(e);

        _otherProducers.signal();
        _firstConsumer.signal();
        _lock.unlock();
    }

    @Override
    public int[] take(int n) throws InterruptedException {
        _lock.lock();
        while (_isFirstConsumerWaiting) {
            _otherConsumers.await();
        }
        while (_nItems < n) {
            _isFirstConsumerWaiting = true;
            _firstConsumer.await();
        }
        _isFirstConsumerWaiting = false;

        int[] ret = new int[n];
        for (int i = 0; i < n; i++)
            ret[i] = super.take();

        _otherConsumers.signal();
        _firstProducer.signal();
        _lock.unlock();
        return ret;
    }
}
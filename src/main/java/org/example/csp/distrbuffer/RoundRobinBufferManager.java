package org.example.csp.distrbuffer;

public class RoundRobinBufferManager extends CspBufferManager {
    public RoundRobinBufferManager(CspBufferCell[] bufferCells, CspChannelProducer[] producers, CspChannelConsumer[] consumers) {
        super(bufferCells, producers, consumers);
    }

    private int _putPtr = 0, _takePtr = 0;

    @Override
    protected int getNextConsumerBuffer() {
        int c = _nItems.length;
        while (c-- > 0) {
            _takePtr = (_takePtr + 1) % _nItems.length;
            if (_nItems[_takePtr] > 0)
                return _takePtr;
        }
        return -1;
    }

    @Override
    protected int getNextProducerBuffer() {
        int c = _nItems.length;
        while (c-- > 0) {
            _putPtr = (_putPtr + 1) % _nItems.length;
            if (_nItems[_putPtr] < Test.CELL_SIZE)
                return _putPtr;
        }
        Test.print(Test.PType.MASTER, "All cells full!");
        return -1;
    }
}

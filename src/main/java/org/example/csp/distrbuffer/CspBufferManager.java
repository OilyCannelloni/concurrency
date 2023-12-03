package org.example.csp.distrbuffer;

import org.jcsp.lang.*;

import java.util.ArrayList;
import java.util.Arrays;


public class CspBufferManager implements CSProcess {
    private final int _producerPivot;
    private final ArrayList<One2OneChannelInt> _pcChannels, _cellChannels;
    protected final int[] _nItems;
    private final Alternative _alternative;
    private int _nProducersRunning, _nConsumersRunning;
    private final CspBufferCell[] _bufferCells;


    public CspBufferManager(
            CspBufferCell[] bufferCells,
            CspChannelProducer[] producers,
            CspChannelConsumer[] consumers
    ) {
        _nProducersRunning = producers.length;
        _nConsumersRunning = consumers.length;
        _producerPivot = producers.length;
        _bufferCells = bufferCells;
        _nItems = new int[bufferCells.length];
        Arrays.fill(_nItems, 0);

        _cellChannels = new ArrayList<>(Arrays.stream(bufferCells).map(c -> c._managerChannel).toList());
        _pcChannels = new ArrayList<>();
        for (CspChannelProducer p : producers)
            _pcChannels.add(p.managerChannel);
        for (CspChannelConsumer c : consumers)
            _pcChannels.add(c.managerChannel);

        Guard[] _guards = _pcChannels.stream().map(One2OneChannelInt::in).toList().toArray(new Guard[]{});
        _alternative = new Alternative(_guards);
    }



    protected int getNextProducerBuffer() {
        int minIndex = 0;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < _nItems.length; i++) {
            if(_nItems[i] < minValue) {
                minIndex = i;
                minValue = _nItems[i];
            }
        }
        if (minValue == Test.CELL_SIZE)
            return -1; // All full
        return minIndex;
    }

    protected int getNextConsumerBuffer() {
        int maxIndex = 0;
        int maxValue = 0;
        for (int i = 0; i < _nItems.length; i++) {
            if(_nItems[i] > maxValue) {
                maxIndex = i;
                maxValue = _nItems[i];
            }
        }

        if (maxValue == 0)
            return -1; // All full
        return maxIndex;
    }

    public void run() {
        while (_nProducersRunning > 0 && _nConsumersRunning > 0) {
            if (Test.SLOW_MANAGER != 0)
                Test.sleep(Test.SLOW_MANAGER);

            // Process all buffer signals
            int index = _alternative.select();
            One2OneChannelInt channel = _pcChannels.get(index);
            Test.print(Test.PType.MANAGER, String.format("\033[91mManager alternative:\033[39m %d", index));


            // Producer ready
            if (index < _producerPivot) {
                // Read opcode from producer
                int msg = channel.in().read();
                if (msg == OpCode.FINISHED) {
                    System.out.printf("\033[91mManager\033[39m Producer at index %d finished!\n", index);
                    _nProducersRunning--;
                    continue;
                }

                // Send ID of next buffer
                int buffer = getNextProducerBuffer();
                Test.print(Test.PType.MANAGER, String.format("\033[91mManager\033[39m picked buffer %d for producer", buffer));
                if (buffer == -1) {
                    channel.out().write(OpCode.FULL); // All full
                    continue;
                }

                channel.out().write(buffer);
                _cellChannels.get(buffer).out().write(OpCode.AWAIT); // READ
                _nItems[buffer]++;
            }

            // Consumer ready
            else {
                int msg = channel.in().read();
                if (msg == OpCode.FINISHED) {
                    System.out.printf("\033[91mManager\033[39m Consumer at index %d finished!\n", index);
                    continue;
                }

                // Send ID of next buffer
                int buffer = getNextConsumerBuffer();
                Test.print(Test.PType.MANAGER, String.format("\033[91mManager\033[39m picked buffer %d for consumer", buffer));
                if (buffer == -1) {
                    channel.out().write(OpCode.EMPTY); // All empty
                    continue;
                }

                channel.out().write(buffer);
                _cellChannels.get(buffer).out().write(OpCode.EXPOSE);
                _nItems[buffer]--;
            }
        }

        System.out.print("\033[91mManager finished!\033[39m \n");

        for (CspBufferCell cell : _bufferCells)
            cell.printStats();
        System.exit(0);
    }
}

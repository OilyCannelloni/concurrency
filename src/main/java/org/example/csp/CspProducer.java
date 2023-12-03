package org.example.csp;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

import java.util.concurrent.ThreadLocalRandom;

public class CspProducer implements CSProcess {
    private final One2OneChannelInt _channel;
    private final int _maxProduce, _loops;

    public CspProducer(One2OneChannelInt channel, int maxProduce, int loops) {
        _channel = channel;
        _maxProduce = maxProduce;
        _loops = loops;
    }

    public void run() {
        for (int i = 0; i < _loops; i++) {
            int item = (ThreadLocalRandom.current().nextInt(_maxProduce)) + 1;
            _channel.out().write(item);
        }
    }
}

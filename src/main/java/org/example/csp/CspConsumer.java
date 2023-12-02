package org.example.csp;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class CspConsumer implements CSProcess {
    private final One2OneChannelInt _channel;
    private final int _maxConsume, _loops;

    public CspConsumer(One2OneChannelInt channel, int maxConsume, int loops) {
        _channel = channel;
        _maxConsume = maxConsume;
        _loops = loops;
    }

    @Override
    public void run() {
        for (int i = 0; i < _loops; i++) {
            int r = _channel.in().read();
            System.out.println(r);
        }
    }
}

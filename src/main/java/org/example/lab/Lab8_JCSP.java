package org.example.lab;


import org.example.csp.CspConsumer;
import org.example.csp.CspProducer;
import org.jcsp.lang.*;

public class Lab8_JCSP {
    public static void main() {
        final One2OneChannelInt channel = Channel.one2oneInt();
        CSProcess[] processes = {
                new CspProducer(channel, 10, 100),
                new CspConsumer(channel, 10, 100)
        };

        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}

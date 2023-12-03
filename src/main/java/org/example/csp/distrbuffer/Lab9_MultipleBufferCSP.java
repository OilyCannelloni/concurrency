package org.example.csp.distrbuffer;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Parallel;
import java.util.LinkedList;
import java.util.List;

public class Lab9_MultipleBufferCSP {
    public static void main() {
        CspBufferCell[] cells = new CspBufferCell[] {
                new CspBufferCell(),
                new CspBufferCell(),
                new CspBufferCell(),
                new CspBufferCell(),
                new CspBufferCell()
        };

        CspChannelProducer[] producers = new CspChannelProducer[] {
                new CspChannelProducer(cells),
                new CspChannelProducer(cells)
        };

        CspChannelConsumer[] consumers = new CspChannelConsumer[] {
                new CspChannelConsumer(cells),
                new CspChannelConsumer(cells)
        };

        CspBufferManager manager = new RoundRobinBufferManager(cells, producers, consumers);


        LinkedList<CSProcess> processes = new LinkedList<>();
        processes.addAll(List.of(cells));
        processes.addAll(List.of(producers));
        processes.addAll(List.of(consumers));
        processes.add(manager);
        Parallel parallel = new Parallel(processes.toArray(new CSProcess[0]));

        System.out.println("Start");
        parallel.run();
    }
}

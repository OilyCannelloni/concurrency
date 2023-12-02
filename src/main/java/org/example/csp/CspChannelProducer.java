package org.example.csp;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;

import java.util.ArrayList;
import java.util.Arrays;

public class CspChannelProducer implements CSProcess {
    static int classId = 0;
    private final int _id = classId++;
    public final One2OneChannelInt managerChannel;
    public final ArrayList<One2OneChannelInt> bufferInChannels;
    private int nLoops = Test.LOOPS;

    public CspChannelProducer(CspBufferCell[] cells) {
        managerChannel = Channel.one2oneInt();
        this.bufferInChannels = new ArrayList<>(Arrays.stream(cells).map(c -> c._inChannel).toList());
    }

    public void run() {
        while (nLoops-- > 0) {
            Test.sleep(Test.PRODUCER_MAX_SLEEP);

            // Signal manager
            managerChannel.out().write(OpCode.READY);

            // Receive buffer ID
            int id = managerChannel.in().read();
            if (id == OpCode.KILL)
                break;

            if (id == OpCode.FULL)
                continue;

            // Write to given buffer
            bufferInChannels.get(id).out().write(_id);
            Test.print(Test.PType.PRODUCER, String.format("\033[33mProducer\033[39m %d wrote to buffer %d\n", _id, id));
        }
        managerChannel.out().write(OpCode.FINISHED);
    }
}

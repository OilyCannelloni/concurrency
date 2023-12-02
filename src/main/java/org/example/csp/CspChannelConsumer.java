package org.example.csp;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;

import java.util.ArrayList;
import java.util.Arrays;

public class CspChannelConsumer implements CSProcess {
    static int classId = 0;
    private final int _id = classId++;
    public final ArrayList<One2OneChannelInt> bufferOutChannels;
    public final One2OneChannelInt managerChannel;
    private int nLoops = Test.LOOPS;

    public CspChannelConsumer(CspBufferCell[] cells) {
        managerChannel = Channel.one2oneInt();
        this.bufferOutChannels = new ArrayList<>(Arrays.stream(cells).map(c -> c._outChannel).toList());
    }

    public void run() {
        while (nLoops-- > 0) {
            Test.sleep(Test.CONSUMER_MAX_SLEEP);

            // Signal Manager
            managerChannel.out().write(OpCode.READY);

            // Receive buffer ID
            int id = managerChannel.in().read();
            if (id == OpCode.KILL)
                break;

            if (id == OpCode.EMPTY)
                continue;

            // Receive item from given buffer
            int item = bufferOutChannels.get(id).in().read();
            Test.print(Test.PType.CONSUMER, String.format("\033[32mConsumer\033[39m %d received item from buffer %d\n", _id, item));
        }
        managerChannel.out().write(OpCode.FINISHED);
    }
}
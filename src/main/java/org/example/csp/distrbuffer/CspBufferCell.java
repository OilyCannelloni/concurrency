package org.example.csp.distrbuffer;

import org.jcsp.lang.*;

public class CspBufferCell implements CSProcess {
    static int classId = 0;
    private final int _id;
    public final One2OneChannelInt _inChannel, _outChannel, _managerChannel;
    private int _nItems = 0, _uses = 0;


    public CspBufferCell() {
        _inChannel = Channel.one2oneInt();
        _outChannel = Channel.one2oneInt();
        _managerChannel = Channel.one2oneInt();
        _id = classId++;
    }

    public void printStats() {
        Test.print(Test.PType.MASTER, String.format("Buffer %d | Uses: %d", _id, _uses));
    }

    public void awaitItem() {
        if (_nItems >= Test.CELL_SIZE) {
            System.out.printf("ERROR: Buffer %d overfill!\n", _id);
            return;
        }

        int x = _inChannel.in().read();
        _nItems++;
        Test.print(Test.PType.BUFFER, String.format("\033[36mBuffer\033[39m %d received item | nItems = %d", _id, _nItems));
    }

    public void exposeItem() {
        if (_nItems <= 0) {
            System.out.printf("ERROR: Buffer %d sending from empty!\n", _id);
            return;
        }

        _outChannel.out().write(_id);
        _nItems--;
        Test.print(Test.PType.BUFFER, String.format("\033[36mBuffer\033[39m %d sent item | nItems = %d", _id, _nItems));
    }

    @Override
    public void run() {
        while (true) {
            // Await an instruction from manager
            int opcode = _managerChannel.in().read();
            if (opcode == OpCode.AWAIT) {
                awaitItem();
            } else if (opcode == OpCode.EXPOSE) {
                exposeItem();
            } else if (opcode == OpCode.KILL) {
                break;
            }
            _uses++;
        }
    }
}

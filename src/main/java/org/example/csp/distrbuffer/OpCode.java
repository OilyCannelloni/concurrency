package org.example.csp.distrbuffer;

public class OpCode {
    public static int
            KILL = -1,
            FINISHED = -2,
            FULL = -3,
            EMPTY = -4,
            AWAIT = -5,
            EXPOSE = -6,
            READY = -7;
}

package org.example.csp;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
    enum PType {
        PRODUCER,
        CONSUMER,
        MANAGER,
        BUFFER,
        MASTER
    }

    static boolean getVerbose(PType type) {
        switch (type) {
            case CONSUMER, PRODUCER, MANAGER -> {return false;}
            case BUFFER, MASTER -> {return true;}
        }
        return false;
    }

    static int
        PRODUCER_MAX_SLEEP = 8,
        CONSUMER_MAX_SLEEP = 20,
        LOOPS = 1000;

    static void sleep(int max) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(max));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void print(PType type, String message) {
        if (getVerbose(type))
            System.out.println(message);
    }
}

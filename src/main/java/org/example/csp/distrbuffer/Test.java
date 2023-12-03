package org.example.csp.distrbuffer;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
    enum PType {
        PRODUCER,
        CONSUMER,
        MANAGER,
        BUFFER,
        MASTER
    }

    // Które procesy mają pisać na terminal
    static boolean getVerbose(PType type) {
        switch (type) {
            case CONSUMER, PRODUCER, MANAGER -> {return true;}
            case BUFFER, MASTER -> {return true;}
        }
        return false;
    }

    static int
        // Konfiguracje:
            // 80 / 200 / 10 -> debug
            // 8 / 20 / 0 -> zapychanie
            // 0 / 0 / 0 -> default, najlepiej wyłączyć terminal powyżej

        // Tempo produkcji i konsumpcji, do testowania zapchanego na full bufora
        PRODUCER_MAX_SLEEP = 80,
        CONSUMER_MAX_SLEEP = 200,
        // Spowolnienie pracy w celu niepomieszania się komunikatów w terminalu
        SLOW_MANAGER = 10,
        // Pętle każdego producenta
        LOOPS = 10,
        // Rozmiar komórki bufora
        CELL_SIZE = 5;

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

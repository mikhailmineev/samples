package mikhailmineev.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * If we remove volatile, application hangs
 */
public class VolatileCase {
    private volatile static boolean stopRequested;
    public static void main(String[] args)
            throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested)
                i++;
        });
        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}


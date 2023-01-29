package mikhailmineev.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * In this example we set interrupt status for a thread
 * Later sleep immediately throws interrupted exception
 */
public class InterruptSleep {

    public static void main(String... args) {
        var thread = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("I am going to sleep");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("I was interrupted");
                    return;
                }
            }
        });
        System.out.println("Sending interrupt");
        thread.interrupt();
        System.out.println("I am going to start");
        thread.start();
    }
}

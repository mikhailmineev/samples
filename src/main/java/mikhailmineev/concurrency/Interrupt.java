package mikhailmineev.concurrency;

/**
 * Exit from thread using interrupts
 */
public class Interrupt {

    public static void main(String... args) {
        var thread = new Thread(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("I was interrupted");
                    return;
                }
            }
        });
        thread.interrupt();
        thread.start();
    }
}

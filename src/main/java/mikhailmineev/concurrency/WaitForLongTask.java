package mikhailmineev.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Let's wait for long task to be done
 */
public class WaitForLongTask {

    final Object lock = new Object();

    volatile boolean isDone = false;

    public void longTask() {
        synchronized (lock) {
            System.out.println("longTask");
            isDone = true;
            lock.notifyAll();
        }
    }

    public void runAfterLongTask(int i) {
        if (!isDone) {
            synchronized (lock) {
                if (!isDone) {
                    try {
                        System.out.println("waiting " + i);
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("interrupted " + i);
                    }
                }
            }
        }
        System.out.println("runAfterLongTask " + i);
    }

    public static void main(String... args) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(6);
        var object = new WaitForLongTask();
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            tasks.add(() -> {object.runAfterLongTask(finalI); return null;});
        }
        tasks.add(() -> {object.longTask(); return null;});
        for (int i = 2; i < 5; i++) {
            int finalI = i;
            tasks.add(() -> {object.runAfterLongTask(finalI); return null;});
        }
        var result = executorService.invokeAll(tasks);
        result.forEach(e -> {
            try {
                e.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });
        executorService.shutdown();
    }
}

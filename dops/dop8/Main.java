package dops.dop8;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock locker = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                testMethod(locker);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        Thread thread2 = new Thread(() -> {
            try {
                testMethod(locker);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();
    }

    private static void testMethod(ReentrantLock locker) throws InterruptedException {
        locker.lock();

        System.out.println("Thread: "  + Thread.currentThread().getName() + " started running");

        Thread.sleep(2000);

        System.out.println("Thread: " + Thread.currentThread().getName() + " finished running");

        locker.unlock();
    }
}
package dops.dop7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


class BarAction implements Runnable {
    public void run() {
    	System.out.println("After barrier: " + Thread.currentThread().getName());
    }
}

public class Main {
    private static final AtomicInteger waitTime = new AtomicInteger(500);

    // Запустивши симуляцію, можемо побачити, що час очікування усіх 3 потоків буде однакомив.
    // Отже, кастомний бар'єр працює коректно.
    public static void main(String[] args) {
        final int PartiesCount = 3;

        CyclicBarrier barrier = new CyclicBarrier(PartiesCount);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < PartiesCount; i++) {
            threads.add(new Thread(() -> {
                try {
                    testMethod(barrier);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        threads.forEach(Thread::start);
    }

    public static void testMethod(CyclicBarrier barrier) throws InterruptedException {
        System.out.println("Before: barrier" + Thread.currentThread().getName());
        Thread.sleep(waitTime.getAndAccumulate(1000, Integer::sum));

        barrier.await(new BarAction());
    }
}
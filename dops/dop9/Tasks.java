package dops.dop9;

import java.util.Random;

public class Tasks {

    public static Runnable task(int number, Phaser phaser) {
        return new Runnable() {
            @Override
            public void run() {

                try {
                    int count = number;
                    for (int i = 1; i < 100; i++) {
                        count++;
                        Thread.sleep(100);
                        System.out.println(Thread.currentThread().getName() + " Value: " + count);
                        if (i * number > 20) {
                            System.out.println(i);
                            phaser.arriveAndDeregister();//сообщает о завершении всех фаз стороной и снимает ее с регистрации. Возвращает номер текущей фазы;
                            break;
                        } else {

                            phaser.arriveAndAwaitAdvance();//поток завершил выполнение фазы. Поток приостанавливается до момента, пока все остальные стороны не закончат выполнять данную фазу.
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
package dops.dop9;

public class Main {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(5);

        Runnable Client1 = Tasks.task(5, phaser);
        Runnable Client2 = Tasks.task(7, phaser);
        Runnable Client3 = Tasks.task(3, phaser);
        Runnable Client4 = Tasks.task(2, phaser);
        Runnable Client5 = Tasks.task(9, phaser);

        new Thread(Client1, "Client-1").start();
        new Thread(Client2, "Client-2").start();
        new Thread(Client3, "Client-3").start();
        new Thread(Client4, "Client-4").start();
        new Thread(Client5, "Client-5").start();
    }
}

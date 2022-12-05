package dops.dop8;

public class ReentrantLock {

    private boolean locked = false;

    public synchronized void lock() throws InterruptedException {
        if (this.locked) {
            this.wait();
        }
        this.locked = true;
    }

    public synchronized void unlock() {
        if (locked) {
            this.locked = false;
            this.notifyAll();
        }
    }
}
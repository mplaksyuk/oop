package dops.dop7;

public class CyclicBarrier {
    private final int maxParties;
    private int currentParties;

    private boolean broken = false;

    public CyclicBarrier(int parties) {
        this.maxParties = this.currentParties = parties;
    }

    public synchronized void await() throws InterruptedException {
        currentParties--;
        if (currentParties == 0) 
        {
            this.broken = true;
            this.currentParties = maxParties;
            notifyAll();
        } 
        else {
            this.broken = false;
            this.wait(); 
        }
    }

    public synchronized void await(Runnable action) throws InterruptedException {
        this.await();

        if (broken)
            action.run();
    }
}
package dops.dop9;

// import java.util.concurrent.Phaser;

public class Phaser {
    private int phase;
    private int parties;
    private int unarrived;
    private boolean terminated;

    public Phaser(int parties) {
        this.phase = 0;//номер этапа
        this.parties = parties;//количество участников
        this.unarrived = parties;

        terminated = false;//состояние завершения
    }


    private synchronized boolean CheckTaskFinished() {
        while(unarrived == 0) {
            unarrived = parties;
            phase++;
            notifyAll();
            System.out.println("Tasks finished. Phase: " + phase);
            return true;
        }
        return false;
    }



    public synchronized void arriveAndAwaitAdvance() throws InterruptedException {//классическое прибытие на барьер
        while (terminated) 
            return;

        unarrived--;

        if(!CheckTaskFinished()) {
            this.wait();
        }
    }

    public synchronized void arriveAndDeregister() {//отменить свое участие
        while (terminated) 
            return;

        parties--;
        unarrived--;
        CheckTaskFinished();

        if(parties == 0) {
            terminated = true;
        }
    }

    public synchronized void register() {
        parties++;
    }

    public int getParties() {
        return parties;
    }

    public int getPhase() {
        return phase;
    }
    public synchronized void arrive(){//сообщить этапщику о своей готовности, не ожидая открытия барьера
        unarrived--;
        CheckTaskFinished();
    }
}
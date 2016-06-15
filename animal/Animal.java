package animal;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import datamodel.DataModel;
import movable.Movable;
import parasite.Parasites;
import datamodel.DataConstant;
import datamodel.DataUnit;
import datamodel.MapDataModel;

public abstract class Animal extends LinkedList<DataUnit> implements DataConstant, Movable, Runnable {
    protected Random random = new Random();
    private boolean isAwake = true;
    private boolean isDead = false;
    private int direction = 0;
    private double delay = 300.0;
    protected final DataModel dataModel;
    private Parasites parasites = null;

    public void speed(double v) {
        if (delay - v < 40.0) {
            System.out.println("已是最大速度!");
            return;
        }
        delay -= v;
    }
    public void parasitic(Parasites parasites) {
        this.parasites = parasites;
    }

    public boolean isParasitic() {
        return parasites != null;
    }

    public DataUnit locate(int x, int y) {
        return dataModel.locate(x, y);
    }

    public Animal(DataModel dataModel) {
        this.dataModel = dataModel;
        addNewBody();
    }

    public abstract void addNewBody();

    public boolean isDead() {
        return isDead;
    }

    public boolean isAwake() {
        return isAwake;
    }

    /**
     * Once the method is invoked, the animal will stand still.
     */
    public void sleep() {
        isAwake = false;
    }

    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void wakeUp() {
        isAwake = true;
    }

    /**
     * Once the method is invoked, this thread will be dead.
     */
    public void die() {
        sleep();
        if (!isDead()) {
            while (size() > 0) {
                DataUnit d = get(0);
                locate(d.x, d.y).setValue(EMPTY);
                remove(d);
            }
        }
        System.out.println("An animal died~!");
    }

    @Override
    public void run() {
            while (!isDead()) {
                try {
                    while (isAwake()) {
                        move();
                        sleep((long) delay);
                    }
                } catch (IndexOutOfBoundsException e) {// size is 0, the thread is die.
                    break;
                }
                sleep(1000);
            }
        die();
    }

    /**
     * This method can not be override.
     *
     * @return The current direction.
     */
    public final int currentDirection() {
        if (parasites != null) {
            /**
             * Parasite to determine your behavior.
             */
            return parasites.currentDirection();
        }
        return direction;
    }

    public void turn(int d) {
        if (parasites != null) {
            /**
             * You can not control yourself.
             */
            return;
        }
        direction = random.nextInt(4);
    }
}

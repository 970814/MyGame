package datamodel;

import animal.Animal;

import java.util.LinkedList;

/**
 * Created by Administrator on 2016/4/24.
 */
public abstract class DataModel extends LinkedList<Runnable> implements DataConstant {
    int width;
    int height;
    int area;
    double rockDensity;
    int rockCount;

    protected DataModel(int width, int height, double density) {
        area = width * height;
        this.width = width;
        this.height = height;
        rockDensity = density;
        rockCount = (int) (rockDensity * area);
        if (rockCount > area) {
            throw new IllegalArgumentException("Too much rock!");
        }
    }

    public void startAll() {
        forEach(runnable -> new Thread(runnable).start());
    }


    public abstract void kill(int hashCode);
    public abstract DataUnit locate(int x, int y);
    public abstract DataUnit emptyLocation();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

package controller;

/**
 * Created by Administrator on 2016/4/28.
 */
public abstract class ControlMapping implements Controllable {
    protected int[] map;

    public ControlMapping(int... keys) {
        mapping(keys);
    }
    public void mapping(int... keys) {
        map = keys;
    }
}

package controller;


import parasite.ControlSet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016/4/27.
 */

/**
 * Respond to all keyboard events.
 * @see ControlSet
 */
public class ControllerSet extends LinkedList<Controllable> implements KeyListener {
    /**
     * current key code.
     */
    private Integer currentKey = null;
//    boolean isReleased = true;

    /**
     * Invoked when a key has been typed.
     * This event occurs when a key press is followed by a key release.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     */
    public void keyPressed(KeyEvent e) {
//        if (isReleased) {
//            isReleased = false;
            currentKey = e.getKeyCode();
            System.out.println(currentKey);
//            new Thread(() ->
//            ).start();
            forEach(controllable -> {
                controllable.control(currentKey);
            });
//        }

    }

    /**
     * Invoked when a key has been released.
     */
    public void keyReleased(KeyEvent e) {
//        isReleased = true;
    }

}

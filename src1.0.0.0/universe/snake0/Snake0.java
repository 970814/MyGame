package universe.snake0;

import javax.swing.*;

import java.awt.*;

/**
 * Created by Administrator on 2016/4/12.
 */
public interface Snake0 {
    char EMPTY = ' ';
    char ROCK = '▇';
    char TRACE = '~';
    char TAIL = '✿';
    char FOOD = '❤';
    char BULLET = '●';
    char BODY = '❂';// ▩⊕♨❂ ❁
    char BODY$ = '@';
    char undigestedFood = '♥';
    char HEAD = 'D';
    char MOUSE = 'M';
    int[] key = { 102, 99, 98, 97, 100, 103, 104, 105 };
    int EAST = 102;
    int SOUTHEAST = 99;
    int SOUTH = 98;
    int SOUTHWEST = 97;
    int WEST = 100;
    int NORTHWEST = 103;
    int NORTH = 104;
    int NORTHEAST = 105;

    int LEFT = 37;
    int UP = 38;
    int RIGHT = 39;
    int DOWN = 40;
    int SW = Toolkit.getDefaultToolkit().getScreenSize().width;
    int SH = Toolkit.getDefaultToolkit().getScreenSize().height;

    char[] DS = { '→', '↓', '←', '↑', };

    int[][] value = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }, };
    Image body = new ImageIcon("images\\body.png").getImage();
    Image rock = new ImageIcon("images\\rock.png").getImage();
    Image head = new ImageIcon("images\\head.png").getImage();
    Image tail = new ImageIcon("images\\tail.png").getImage();
    Image undigested = new ImageIcon("images\\undigestedFood.png").getImage();
    Image egg = new ImageIcon("./images/egg.jpg").getImage();
    Image mouse = new ImageIcon("./images/mouse.jpg").getImage();
    Image empty = new ImageIcon("images\\bg.jpg").getImage();
    // HashMap<Integer, int[]> inputMap = new HashMap<>();
    // static {
    // inputMap.p
    // }
}

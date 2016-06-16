package universe.map;

/**
 * Created by Administrator on 2016/4/12.
 */

import universe.img.IMG;
import universe.movable.Movable;
import universe.snake0.Snake0;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/11.
 */
public class MapFrame extends JFrame implements Snake0, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    static {
        try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            // UIManager.getInstalledLookAndFeels()I
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static boolean runAllThread = true;
    ArrayList<Snake> inControlSnakes = new ArrayList<>();
    ArrayList<Snake> outOfControlSnakes = new ArrayList<>();

    public synchronized void killAInControlSnake(int index) {
        if (inControlSnakes.size() < index + 1) {
            return;
        }
        inControlSnakes.get(index).died();
    }

    public synchronized void killAOutOfControlSnake(int index) {
        if (outOfControlSnakes.size() < index + 1) {
            return;
        }
        outOfControlSnakes.get(index).died();
    }

    public synchronized void changeAInControlSnakeState(int index) {
        if (inControlSnakes.size() < index + 1) {
            return;
        }
        if (inControlSnakes.get(index).isAlive0()) {
            inControlSnakes.get(index).stop0();
        } else {
            inControlSnakes.get(index).awake();
        }
    }

    public synchronized void changeAOutOfControlSnakeState(int index) {
        if (outOfControlSnakes.size() < index + 1) {
            return;
        }
        if (outOfControlSnakes.get(index).isAlive0()) {
            outOfControlSnakes.get(index).stop0();
        } else {
            outOfControlSnakes.get(index).awake();
        }
    }

    public synchronized void changeAllSnakesState() {
        for (int i = 0; i < inControlSnakes.size(); i++) {
            changeAInControlSnakeState(i);
        }
        for (int i = 0; i < outOfControlSnakes.size(); i++) {
            changeAOutOfControlSnakeState(i);
        }
    }

    private final int fps = 25;

    @Override
    public void run() {
        // this0.getSize();
        while (true) {
            try {
                Thread.sleep(fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public class Mouse extends Snake {
        public Mouse() {
            super(1);
        }

        public Mouse(int delay) {
            super(1, delay);
        }

        @Override
        public synchronized void move() {
            Point head = snake.get(0);
            int d = getDirection();// Because of thread
            int i = head.x + value[d][0];
            int j = head.y + value[d][1];
            for (int x = 0; x < 4; x++) {
                if (map[i][j] == EMPTY || map[i][j] == FOOD) {
                    map[head.x][head.y] = EMPTY;
                    map[i][j] = MOUSE;
                    snake.remove(head);
                    snake.add(new Point(i, j));
                    break;
                }
                d++;
                d %= 4;
                i = head.x + value[d][0];
                j = head.y + value[d][1];
            }
            setDirection(d);
        }

        @Override
        protected synchronized void reverse() {

        }
    }

    public abstract class Snake extends Thread implements Snake0, Movable {
        public String toString() {
            return "V: " + String.format("%.1f", 1000.0 / delay)
                    + " (G/S), D: " + DS[getDirection()] + ", L: " + size()
                    + ", S: " + (isAlive0() ? 1 : 0);
        };

        ArrayList<Point> snake = new ArrayList<>();
        boolean isAlive = true;
        protected boolean isDied = false;
        int initSize;
        int direction = 0;
        double delay = 300.0;

        // double speed = 1000/delay;
        boolean isDied() {
            return isDied;
        }

        boolean isAlive0() {
            return isAlive;
        }

        void stop0() {
            isAlive = false;
        }

        void awake() {
            isAlive = true;
        }

        public synchronized void died() {
            stop0();
            if (!isDied) {
                isDied = true;
                while (snake.size() > 0) {
                    Point p = snake.get(0);
                    map[p.x][p.y] = EMPTY;
                    snake.remove(p);
                }
                if (!(this instanceof Mouse)) {
                    System.out.println("snake died~!");
                } else {
                    System.out.println("mouse died~!");
                }
                inControlSnakes.remove(this);
                outOfControlSnakes.remove(this);

            } else {
                System.out.println("snake has already died!");
            }
        }

        protected synchronized void eatMouse(int x, int y) {
            for (int i = 0; i < outOfControlSnakes.size(); i++) {
                Snake mouse = outOfControlSnakes.get(i);
                if (mouse instanceof Mouse) {
                    Point p;
                    // try {
                    p = mouse.snake.get(0);
                    // } catch (IndexOutOfBoundsException e) {
                    // e.printStackTrace();
                    // return;
                    // }
                    if (p.x == x && p.y == y) {
                        mouse.died();
                        System.out.println("eat a mouse");
                        return;
                    }
                }
            }
        }

        public Snake() {
            this(3);
        }

        public Snake(int initSize) {
            this.initSize = initSize;
            setLength(initSize);
        }

        public Snake(int initSize, int delay) {
            this(initSize);
            this.delay = delay;
        }

        public int size() {
            return snake.size();
        }

        private void setLength(int x) {
            int i;
            int j;
            do {
                i = random.nextInt(width / 2);
                j = random.nextInt(height / 2);
            } while (map[i][j] != EMPTY);
            ds[i][j] = direction;
            snake.add(new Point(i, j));
            for (int q = 1; q < x; q++) {
                snake.add(0, new Point(snake.get(q - 1).x, snake.get(q - 1).y
                        + i));
                map[snake.get(0).x][snake.get(0).y] = BODY;
                ds[snake.get(0).x][snake.get(0).y] = direction;
            }
        }

        protected void cut(int i, int j) {
            for (int x = 1; x < snake.size(); x++) {
                if (snake.get(x).x == i && snake.get(x).y == j) {
                    int size = snake.size() - x;
                    for (int q = 0; q < size; q++) {
                        snake.remove(snake.size() - 1);
                    }
                    break;
                }
            }
        }

        protected synchronized void reverse(Point tail) {
            ArrayList<Point> reverse = new ArrayList<>();
            reverse.add(0, tail);
            reverse(reverse);
        }

        protected synchronized void reverse(ArrayList<Point> reverse) {

            for (int i = snake.size() - 1; i >= 0; i--) {
                reverse.add(snake.get(i));
            }
            snake = reverse;
            map[snake.get(0).x][snake.get(0).y] = HEAD;
            map[snake.get(snake.size() - 1).x][snake.get(snake.size() - 1).y] = TAIL;
            for (int i = 0; i < snake.size(); i++) {
                Point n = snake.get(i);
                if (map[n.x][n.y] == BODY$) {
                    continue;
                }
                int d = ds[n.x][n.y];
                d++;
                d %= 4;
                d++;
                d %= 4;
                ds[n.x][n.y] = d;
            }
        }

        protected synchronized void reverse() {
            reverse(new ArrayList<>());
        }

        protected void sleep() {
            try {
                Thread.sleep((long) delay);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.exit(-1);
            }
        }

        @Override
        public void run() {
            try {
                while (runAllThread) {
                    try {
                        while (isAlive0()) {
                            move();
                            sleep();
                            delay += (random.nextBoolean() ? 1 : -1)
                                    * Math.random();
                            if (delay <= fps) {
                                delay = 50;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {// 蛇长度为0，线程死亡
                        // isAlive = false;
                        // e.printStackTrace();
                        if (snake.size() != 0) {
                            System.out.println("died size: " + snake.size());
                        }
                        break;
                    }
                    if (isDied()) {
                        break;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            died();
        }

        @Override
        public void move() {// Artificial Intelligence

            Point head = snake.get(0);
            Point tail = snake.get(snake.size() - 1);
            int oldD = ds[head.x][head.y];
            int d = getDirection();// Because of thread
            int i = head.x + value[d][0];
            int j = head.y + value[d][1];

            for (int x = 0; x < 4; x++) {
                if (map[i][j] == EMPTY || map[i][j] == FOOD
                        || map[i][j] == MOUSE) {
                    break;
                }
                d++;
                d %= 4;
                i = head.x + value[d][0];
                j = head.y + value[d][1];
            }
            setDirection(d);
            Point oldTail = null;
            if (map[i][j] != FOOD || map[i][j] == MOUSE) {// 如果不是食物//&&
                // map[i][j] !=
                // undigestedFood
                map[tail.x][tail.y] = EMPTY;
                snake.remove(snake.size() - 1);
                oldTail = tail;
                if (tail.x == i && tail.y == j) {
                    d++;
                    d %= 4;
                    i = head.x + value[d][0];
                    j = head.y + value[d][1];
                    setDirection(d);
                }
            }
            // boolean hasMoved = false;
            if (map[i][j] == EMPTY || map[i][j] == FOOD || map[i][j] == MOUSE) {// ||
                // map[i][j]
                // ==
                // undigestedFood
                if (snake.size() > 0) {
                    if (oldD != d) {
                        map[head.x][head.y] = BODY$;
                        ds[head.x][head.y] = IMG.getDirection$(oldD, d);
                    } else {
                        map[head.x][head.y] = BODY;
                    }
                }
                // if (map[i][j] == FOOD) {
                // food--;
                // }
                if (map[i][j] == MOUSE) {
                    eatMouse(i, j);
                }
                snake.add(0, new Point(i, j));
                // hasMoved = true;
            } else {
                reverse(tail);
                return;
            }
            tail = snake.get(snake.size() - 1);
            if (oldTail != null) {
                if (map[tail.x][tail.y] == BODY$) {
                    ds[tail.x][tail.y] = IMG.getDirection$0(
                            ds[oldTail.x][oldTail.y], ds[tail.x][tail.y]);
                }
            }
            if (snake.size() > 1) {
                map[tail.x][tail.y] = TAIL;// 尾巴
            }
            map[snake.get(0).x][snake.get(0).y] = HEAD;// 蛇头
            // if (hasMoved) {
            ds[snake.get(0).x][snake.get(0).y] = d;
            // }
        }

        @Override
        public int getDirection() {
            return direction;// 默认蛇不受到控制
        }

        @Override
        public void setDirection(int d) {
            direction = d;
        }
    }

    public class SnakeFactory {

        public Mouse getMouse0(int delay){
            return new Mouse(delay) {
                public void move() {
                    Point head = snake.get(0);
                    int d = getDirection();
                    int i = head.x + value[d][0];
                    int j = head.y + value[d][1];

                    if (map[i][j] == EMPTY || map[i][j] == FOOD) {
                        map[head.x][head.y] = EMPTY;
                        map[i][j] = MOUSE;
                        snake.remove(head);
                        snake.add(new Point(i, j));
                    }
                    setDirection(d);
                }

                public int getDirection() {
                    return this0.direction;
                }

                public void setDirection(int d) {
                    // do nothing
                }

                {
                    start();
                }
            };
        }

        public Mouse getMouse2(int delay){
            return new Mouse(delay) {
                {
                    start();
                }

                @Override
                public int getDirection() {
                    return this0.direction;
                }

                @Override
                public void setDirection(int d) {
                    this0.direction = d;
                }
            };
        }

        public Mouse getMouse3(int delay) {
            return new Mouse(delay) {
                {
                    start();
                }
            };
        }

        public Mouse getMouse4(int delay) {
            return new Mouse(delay) {
                @Override
                public void move() {
                    Point head = snake.get(0);
                    int d = getDirection();
                    int i = head.x + value[d][0];
                    int j = head.y + value[d][1];
                    final int i0 = i;
                    final int j0 = j;
                    int ld = d == 0 ? 3 : d - 1;
                    int li = head.x + value[ld][0];
                    int lj = head.y + value[ld][1];
                    int rd = d + 1;
                    rd %= 4;
                    int ri = head.x + value[rd][0];
                    int rj = head.y + value[rd][1];

                    if ((map[li][lj] == EMPTY || map[li][lj] == FOOD)
                            || (map[ri][rj] == EMPTY || map[ri][rj] == FOOD)
                            || (map[i0][j0] == EMPTY || map[i0][j0] == FOOD)) {
                        // default is i, j
                        if (map[li][lj] == EMPTY || map[li][lj] == FOOD) {
                            boolean flag = true;
                            if (map[i0][j0] == EMPTY || map[i0][j0] == FOOD) {
                                if (random.nextInt(40) > 1) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                i = li;
                                j = lj;
                                d = ld;
                            }
                        }
                        if (map[ri][rj] == EMPTY || map[ri][rj] == FOOD) {
                            boolean flag = true;
                            if ((map[li][lj] == EMPTY || map[li][lj] == FOOD)
                                    || (map[i0][j0] == EMPTY || map[i0][j0] == FOOD)) {
                                if (random.nextInt(40) > 1) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                i = ri;
                                j = rj;
                                d = rd;
                            }
                        } // default is i, j
                    } else {// // if (snakes[l].size() == 1) 下面的代码有效或者转置时
                        d++;
                        d %= 4;
                        d++;
                        d %= 4;
                        i = head.x + value[d][0];
                        j = head.y + value[d][1];
                    }

                    setDirection(d);

                    if (map[i][j] == EMPTY || map[i][j] == FOOD) {
                        map[head.x][head.y] = EMPTY;
                        map[i][j] = MOUSE;
                        snake.remove(head);
                        snake.add(new Point(i, j));
                    }
                }

                {
                    start();
                }
            };
        }

        public Snake getSnake0(int length, int delay) {
            return new Snake(length, delay) {
                @Override
                public synchronized void move() {
                    Point head = snake.get(0);
                    Point tail = snake.get(snake.size() - 1);
                    int oldD = ds[head.x][head.y];

                    int d = getDirection();// Because of thread
                    /**
                     * 保证蛇不能倒着走
                     */
                    int d0 = d;
                    d0++;
                    d0 %= 4;
                    d0++;
                    d0 %= 4;
                    if (d0 == oldD) {
                        d = oldD;
                    }

                    int i = head.x + value[d][0];
                    int j = head.y + value[d][1];
                    Point oldTail = null;
                    if (map[i][j] != FOOD && map[i][j] != MOUSE) {// 如果不是食物
                        map[tail.x][tail.y] = EMPTY;
                        snake.remove(snake.size() - 1);
                        oldTail = tail;
                    }
                    boolean hasMoved = false;
                    if (map[i][j] == EMPTY || map[i][j] == FOOD
                            || map[i][j] == MOUSE) {
                        if (snake.size() > 0) {
                            // map[head.x][head.y] = map[i][j] == EMPTY ?
                            // BODY : undigestedFood;
                            if (oldD != d) {
                                map[head.x][head.y] = BODY$;
                                ds[head.x][head.y] = IMG.getDirection$(
                                        oldD, d);
                            } else {
                                map[head.x][head.y] = BODY;
                            }
                        }
                        // if (map[i][j] == FOOD) {
                        // food--;
                        // }
                        if (map[i][j] == MOUSE) {
                            // cut(i, j);
                            eatMouse(i, j);
                        }
                        snake.add(0, new Point(i, j));

                        hasMoved = true;
                    }

                    tail = snake.get(snake.size() - 1);
                    if (oldTail != null) {
                        if (map[tail.x][tail.y] == BODY$) {
                            ds[tail.x][tail.y] = IMG.getDirection$0(
                                    ds[oldTail.x][oldTail.y],
                                    ds[tail.x][tail.y]);
                        }
                    }
                    if (snake.size() > 1) {
                        map[tail.x][tail.y] = TAIL;// 尾巴
                    }
                    map[snake.get(0).x][snake.get(0).y] = HEAD;// 蛇头
                    if (hasMoved) {
                        ds[snake.get(0).x][snake.get(0).y] = d;
                    }
                }

                @Override
                public int getDirection() {
                    return this0.direction;
                }

                @Override
                public void setDirection(int d) {
                    // this0.direction = d;
                }

                {
                    start();
                }
            };
        }

        public Snake getSnake2(int length, int delay) {
            return new Snake(length, delay) {
                @Override
                public int getDirection() {
                    return this0.direction;
                }

                @Override
                public void setDirection(int d) {
                    this0.direction = d;
                }

                {
                    start();
                }
            };
        }

        public Snake getSnake3(int length, int delay) {
            return new Snake(length, delay) {
                {
                    start();
                }
            };
        }

        public Snake getSnake4(int length, int delay) {
            return new Snake(length, delay) {
                @Override
                public void move() {// Artificial
                    // Intelligence
                    Point head = snake.get(0);
                    Point tail = snake.get(snake.size() - 1);
                    int oldD = ds[head.x][head.y];
                    int d = getDirection();// Because
                    // of
                    // thread
                    int i = head.x + value[d][0];
                    int j = head.y + value[d][1];
                    final int i0 = i;
                    final int j0 = j;
                    int ld = d == 0 ? 3 : d - 1;
                    int li = head.x + value[ld][0];
                    int lj = head.y + value[ld][1];
                    int rd = d + 1;
                    rd %= 4;
                    int ri = head.x + value[rd][0];
                    int rj = head.y + value[rd][1];

                    if ((map[li][lj] == EMPTY || map[li][lj] == FOOD || map[li][lj] == MOUSE)
                            || (map[ri][rj] == EMPTY || map[ri][rj] == FOOD || map[ri][rj] == MOUSE)
                            || (map[i0][j0] == EMPTY || map[i0][j0] == FOOD || map[i0][j0] == MOUSE)) {
                        // default is i, j
                        if (map[li][lj] == EMPTY || map[li][lj] == FOOD
                                || map[li][lj] == MOUSE) {
                            boolean flag = true;
                            if (map[i0][j0] == EMPTY || map[i0][j0] == FOOD
                                    || map[i0][j0] == MOUSE) {
                                if (random.nextInt(40) > 1) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                i = li;
                                j = lj;
                                d = ld;
                            }
                        }
                        if (map[ri][rj] == EMPTY || map[ri][rj] == FOOD
                                || map[ri][rj] == MOUSE) {
                            boolean flag = true;
                            if ((map[li][lj] == EMPTY
                                    || map[li][lj] == FOOD || map[li][lj] == MOUSE)
                                    || (map[i0][j0] == EMPTY
                                    || map[i0][j0] == FOOD || map[i0][j0] == MOUSE)) {
                                if (random.nextInt(40) > 1) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                i = ri;
                                j = rj;
                                d = rd;
                            }

                        } // default is i, j
                    } else {// // if (snakes[l].size() == 1) 下面的代码有效或者转置时
                        d++;
                        d %= 4;
                        d++;
                        d %= 4;
                        i = head.x + value[d][0];
                        j = head.y + value[d][1];
                    }
                    // if (snake.size() == 1) {
                    //
                    // }

                    setDirection(d);
                    Point oldTail = null;
                    if (map[i][j] != FOOD && map[i][j] != MOUSE) {// 如果不是食物//&&
                        // map[i][j] !=
                        // undigestedFood
                        map[tail.x][tail.y] = EMPTY;
                        snake.remove(snake.size() - 1);
                        oldTail = tail;
                        if (tail.x == i && tail.y == j) {
                            d++;// 强迫其改变方向
                            d %= 4;
                            i = head.x + value[d][0];
                            j = head.y + value[d][1];
                            setDirection(d);
                        }
                    }

                    if (map[i][j] == EMPTY || map[i][j] == FOOD
                            || map[i][j] == MOUSE) {// ||
                        // map[i][j]
                        // ==
                        // undigestedFood

                        if (snake.size() > 0) {
                            if (oldD != d) {
                                map[head.x][head.y] = BODY$;
                                ds[head.x][head.y] = IMG.getDirection$(
                                        oldD, d);
                            } else {
                                map[head.x][head.y] = BODY;
                            }
                        }
                        // if (map[i][j] == FOOD) {
                        // food--;
                        // }

                        if (map[i][j] == MOUSE) {
                            eatMouse(i, j);
                            // cut(i, j);
                        }
                        snake.add(0, new Point(i, j));
                    } else {
                        reverse(tail);
                        return;
                    }
                    tail = snake.get(snake.size() - 1);
                    if (oldTail != null) {
                        if (map[tail.x][tail.y] == BODY$) {
                            ds[tail.x][tail.y] = IMG.getDirection$0(
                                    ds[oldTail.x][oldTail.y],
                                    ds[tail.x][tail.y]);
                        }
                    }
                    if (snake.size() > 1) {
                        map[tail.x][tail.y] = TAIL;// 尾巴
                    }
                    map[snake.get(0).x][snake.get(0).y] = HEAD;// 蛇头
                    // if (hasMoved) {
                    ds[snake.get(0).x][snake.get(0).y] = d;
                }

                {
                    start();
                }
            };
        }

    }

    private final MapFrame this0 = this;
    private final SnakeFactory snakeFactory = new SnakeFactory();
    public static final Random random = new Random();
    public final char[][] map;
    public int[][] ds;
    public int height;
    public int width;
    private int area;
    private double rockDensity;
    private int rockCount;
    private int direction = 0;
    private int delay = 150;
    private int currentKey = -1;
    protected boolean showInfo = false;
    public int getRandomValue() {
        return random.nextInt(300) + fps * 2;
    }

    private int arraySearch(int[] a, int key) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    int[] input = {};
    private final KeyListener listener = new KeyAdapter() {

        // int n = 0;
        /**
         * 0: STOP 1: KILL
         */
        int[][] command = new int[][] { { 83, 84, 79, 80, },
                { 75, 73, 76, 76, }, };

        int[] values = new int[] { 10, 96, 97, 98, 99, 100, 101, 102, 103, 104,
                105, };

        /**
         *
         * @param key
         *            键盘输入值
         * @return 如果返回假,则按键将被进一步进行响应,反之,则代表输入了一条指令。将返回真，不继续进行后续的指令.
         */
        boolean checkInput(int key) {
            int l = input.length;
            int i = Arrays.binarySearch(values, key);
            if (i >= 0) {
                if (l < 4) {

                } else if (key == 10) {// 输入回车
                    runCommand();
                    input = new int[] {};
                    return true;
                } else {
                    input = Arrays.copyOf(input, input.length + 1);
                    input[l] = key;
                    return false;
                }
            } else {
                i = arraySearch(command[0], key);
                if (l == i) {
                    input = Arrays.copyOf(input, input.length + 1);
                    input[l] = key;
                    return false;
                }
                i = arraySearch(command[1], key);
                if (l == i || l - 1 == i) {
                    input = Arrays.copyOf(input, input.length + 1);
                    input[l] = key;
                    return false;
                }
            }
            input = new int[] {};
            return false;
        }

        private void runCommand() {
            int[] newArray = Arrays.copyOf(input, 4);
            if (Arrays.equals(newArray, command[0])) {
                if (input.length - 4 == 0) {
                    changeAllSnakesState();
                } else {
                    char[] chs = new char[input.length - 4];
                    for (int k = 0; k < chs.length; k++) {
                        chs[k] = (char) (input[k + 4] - values[1] + '0');
                    }
                    System.out.println(new String(chs));
                    changeAOutOfControlSnakeState(Integer.parseInt(new String(
                            chs)));
                }
            } else {
                if (input.length - 4 == 0) {
                    killAllSnake();
                } else {
                    char[] chs = new char[input.length - 4];
                    for (int k = 0; k < chs.length; k++) {
                        chs[k] = (char) (input[k + 4] - values[1] + '0');
                    }
                    System.out.println(new String(chs));
                    killAOutOfControlSnake(Integer.parseInt(new String(chs)));
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int d = currentKey = e.getKeyCode();
            if (checkInput(d)) {
                return;
            }
            int d0 = 0;
            boolean isChanged = false;
            switch (d) {

                case 38:
                    d0++;
                case 37:
                    d0++;
                case 40:
                    d0++;
                case 39:
                    isChanged = true;
                    break;// mapping
                // the
                // direction
                // key

                case 32:// space
                    showInfo = !showInfo;
                    break;

                case 27:// Escape
                    // setBounds(0, 0,
                    // Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                    // Toolkit.getDefaultToolkit().getScreenSize().height / 2);
                    if (!isMaximized()) {
                        if (JOptionPane.showConfirmDialog(this0, "退出游戏？", "贪吃蛇",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE) == 0)
                            System.exit(0);
                    } else {
                        changeState();
                    }
                    break;
                case 49:// Number 1
                    inControlSnakes.add(snakeFactory.getMouse0(getRandomValue()));
                    break;
                case 50:// Number
                    // 2
                    inControlSnakes.add(snakeFactory.getMouse2(getRandomValue()));
                    break;
                case 51:// Number
                    // 3:
                    outOfControlSnakes.add(snakeFactory.getMouse3(getRandomValue()));
                    break;
                case 52:// Number
                    // 4:
                    outOfControlSnakes.add(snakeFactory.getMouse4(getRandomValue()));
                    break;
                case 53: // Number 5
                    new Thread(() -> {// 每次随机产生的食物数目
                        for (int i = 0; i < 20; i++) {
                            productFood();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case 82:// R
                    if (inControlSnakes.size() > 0) {
                        inControlSnakes.get(0).reverse();
                    }
                    break;
                case 112:// F1
                    inControlSnakes.add(snakeFactory.getSnake0(9, getRandomValue()));
                    break;
                case 113:// F2
                    inControlSnakes.add(snakeFactory.getSnake2(9, getRandomValue()));
                    break;
                case 114:// F3
                    outOfControlSnakes.add(snakeFactory.getSnake3(9, getRandomValue()));
                    break;
                case 115:// F4
                    outOfControlSnakes.add(snakeFactory.getSnake4(9, getRandomValue()));
                    break;
                case 116:// F5
                    changeAInControlSnakeState(0);
                    break;

                case 117:// F6
                    changeAOutOfControlSnakeState(0);
                    break;
                case 118:// F7
                    killAInControlSnake(0);
                    break;
                case 119:// F8
                    killAOutOfControlSnake(0);
                    break;
                case 122:// F11
                    changeAllSnakesState();
                    break;
                case 123:// F12
                    // runAllThread = runAllThread == true ? false : true;// it will
                    // kill all current thread or restart
                    changeState();
                    break;

                default:
                    break;
            }
            if (isChanged) {
                /**
                 * 设置反方向无效
                 */
                // d = d0 + 1;
                // d %= 4;
                // d++;
                // d %= 4;
                // if (d == direction) {
                // return;
                // }
                direction = d0;
            }
            // this0.repaint();
        }
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MapFrame mapFrame = new MapFrame(71, 33, 0.0);
            mapFrame.setVisible(true);
        });
    }

    protected void killAllSnake() {
        while (inControlSnakes.size() > 0) {
            killAInControlSnake(0);
        }
        while (outOfControlSnakes.size() > 0) {
            killAOutOfControlSnake(0);
        }
    }

    int[] a = new int[] { 1, 2, 3, 4 };
    int[] b = new int[] { 2, 3, 4, 4 };

    int s = 3;

    protected void changeState() {
        s++;
        s %= 4;
        setBounds(0, 0, SW * a[s] / b[s], SH * a[s] / b[s]);

    }

    protected boolean isMaximized() {
        return getWidth() == SW && getHeight() == SH;
    }

    public void productFood() {// 随机产生食物
        int x = random.nextInt(height) + 1;
        int y = random.nextInt(width) + 1;
        if (map[x][y] == EMPTY) {
            map[x][y] = FOOD;
            // food++;
        }
    }

    private void productRock0() {// 仅仅在初始化时被调用
        int rocks = 0;
        while (rocks < rockCount) {
            if (productRock()) {
                rocks++;
            }
        }
    }

    private synchronized boolean productRock() {// use this0 lock
        int i = random.nextInt(height) + 1;
        int j = random.nextInt(width) + 1;
        if (map[i][j] == EMPTY) {
            map[i][j] = ROCK;
            return true;
        }
        return false;
    }

    private void toEmptyMap() {
        /**
         * clear all.
         */
        for (char[] aMap : map) {
            Arrays.fill(aMap, EMPTY);
        }
        /**
         * initialize the wall.
         */
        for (int i = 0; i < height + 2; i++) {
            map[i][0] = map[i][width + 1] = ROCK;

        }
        for (int i = 0; i < width + 2; i++) {
            map[0][i] = map[height + 1][i] = ROCK;
        }
    }

    private void initMap() {
        toEmptyMap();
        productRock0();
    }

    public MapFrame(int width, int height, double density) {
        this.width = width;
        this.height = height;
        area = height * width;
        rockDensity = density;
        rockCount = (int) (rockDensity * area);
        if (rockCount > area) {
            throw new IllegalArgumentException("Too much rock!");
        }
        map = new char[height + 2][width + 2];// 容纳围墙
        ds = new int[height + 2][width + 2];
        initMap();

        addKeyListener(listener);

        setContentPane(new JComponent() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;
            int baseX = 20;
            int sx = 5;
            int ix = 210;
            int fSize = 15;
            Font font = new Font("华文雅黑", Font.BOLD, fSize);

            @Override
            public void paintComponent(Graphics g) {
                Graphics2D d = (Graphics2D) g;

                int w = this0.getWidth();
                int h = this0.getHeight();
                float size = (w - baseX) / (width + 2);

                d.setFont(d.getFont().deriveFont(100.0f));
                int y = h - 50;
                int x = w - 150;
                switch (direction) {
                    case 0:
                        d.drawString("" + DS[direction], x + 40, y);
                        break;
                    case 1:
                        d.drawString("" + DS[direction], x + 20, y + 40);
                        break;
                    case 2:
                        d.drawString("" + DS[direction], x - 40, y);
                        break;
                    case 3:
                        d.drawString("" + DS[direction], x + 20, y - 40);
                        break;
                }
                int i = 0;
                for (; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        Image img = null;
                        switch (map[i][j]) {
                            case EMPTY:
                                img = empty;
                                break;
                            case ROCK:
                                img = rock;
                                break;
                            case TRACE:
                                break;
                            case HEAD:
                                img = IMG.HS[ds[i][j]];
                                break;
                            case TAIL:
                                img = IMG.TS[ds[i][j]];
                                break;
                            case FOOD:
                                img = egg;
                                break;
                            case BULLET:
                                d.setColor(Color.BLUE);
                                break;
                            case undigestedFood:
                                img = undigested;
                                break;
                            case BODY:
                                img = IMG.BS[ds[i][j]];
                                break;
                            case BODY$:
                                img = IMG.BS$[ds[i][j]];
                                break;
                            case MOUSE:
                                img = mouse;
                                break;
                            default:
                                d.setColor(Color.BLACK);
                                break;
                        }
                        if (img != null) {
                            d.drawImage(img, (int) (baseX + j * size),
                                    (int) (i * size), (int) size, (int) size,
                                    null);
                        }
                    }
                }
                d.setFont(font);
                i++;
                i *= size;
                int i0 = i;
                d.drawString("Key: "
                        + (currentKey == -1 ? "null" : (char) currentKey)
                        + ", value: " + currentKey, sx, i += fSize);
                d.drawString("FPS(帧数): " + String.format("%.1f", 1000.0 / fps),
                        sx, i += fSize);
                d.drawString("Direction(方向): " + DS[direction], sx,
                        i += font.getSize());
                d.drawString("InControlSnakes size: " + inControlSnakes.size(),
                        sx, i += fSize);
                d.drawString(
                        "outOfControlSnakes size: " + outOfControlSnakes.size(),
                        sx, i += fSize);

                if (inControlSnakes.size() > 0) {
                    d.drawString(inControlSnakes.get(0).toString(), sx,
                            i += fSize);
                } else {
                    d.drawString("No-Snake which you can control!", sx,
                            i += fSize);
                }

                d.drawString(Arrays.toString(input), sx, i += fSize);
                char[] chs = new char[input.length];
                for (int k = 0; k < chs.length; k++) {
                    chs[k] = (char) input[k];
                }
                d.drawString(Arrays.toString(chs), sx, i += fSize);
                if (showInfo) {
                    paintSnakeInfo(d, ix, i0 - 500, w, h);
                } else {

                }
            }

            protected void paintSnakeInfo(Graphics2D d, int ix, int j, int w,
                                          int h) {
                d.setColor(Color.YELLOW);
                if (outOfControlSnakes.size() > 0) {
                    int j0 = j;
                    for (int i = 0; i < outOfControlSnakes.size(); i++) {
                        if (i % 30 == 29) {
                            j = j0;
                            ix += this.ix + 90;
                        }
                        d.drawString(i + ": "
                                        + outOfControlSnakes.get(i).toString(), ix,
                                j += fSize);
                    }
                } else {
                    d.setFont(d.getFont().deriveFont(100.0f));
                    d.drawString("No snake message!", ix, j += fSize);
                }
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
        // Toolkit.getDefaultToolkit().getScreenSize().height);
        new Thread(this).start();
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setAlwaysOnTop(true);
    }

    public synchronized Point emptyPoint() {
        int i;
        int j;
        do {
            i = random.nextInt(width) + 1;
            j = random.nextInt(height) + 1;
        } while (map[i][j] != EMPTY);
        return new Point(i, j);
    }
}

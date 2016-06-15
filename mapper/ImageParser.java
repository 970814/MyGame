package mapper;

import datamodel.DataConstant;
import datamodel.DataUnit;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2016/4/25.
 */
public class ImageParser implements DataConstant {
    private static int length = 4;
    private static Image[] HS = new Image[length];
    private static Image[] BS = new Image[length];
    private static Image[] BS$ = new Image[length];
    private static Image[] TS = new Image[length];
    private static Image[] MS = new Image[]{new ImageIcon("images/mouse.jpg").getImage(), new ImageIcon("images/mouse2.jpg").getImage()};

    private static Image getImageAt(String parent, String imgName) {
        return new ImageIcon(parent + imgName).getImage();
    }

    private static Image getImageAt(String imgName) {
        return new ImageIcon(imgName).getImage();
    }

    static {
        for (int i = 0; i < length; i++) {
            HS[i] = getImageAt("images/snake/head" + i + ".jpg");
            BS[i] = getImageAt("images/snake/body" + i + ".jpg");
            BS$[i] = getImageAt("images/snake/body$" + i + ".jpg");
            TS[i] = getImageAt("images/snake/tail" + i + ".jpg");
        }
    }

    private static Image rock = new ImageIcon("images/rock.png").getImage();
    private static Image empty = new ImageIcon("images/bg.jpg").getImage();
    private static Image mouse = new ImageIcon("images/mouse.jpg").getImage();
    private static Image undigested = new ImageIcon("images/undigestedFood.png").getImage();
    private static Image egg = new ImageIcon("images/egg.jpg").getImage();


    /**
     *
     * @param dataUnit the input argument.
     * @return a image which has parsed by dataPoint.
     */
    public static Image parseOf(DataUnit dataUnit) {
        Image img = null;
        switch (dataUnit.getValue()) {
            case EMPTY:
                img = empty;
                break;
            case ROCK:
                img = rock;
                break;
            case FOOD:
                img = egg;
                break;
            case HEAD:
                img = HS[dataUnit.getDirection()];
                break;
            case TAIL:
                img = TS[dataUnit.getDirection()];
                break;
            case BODY:
                img = BS[dataUnit.getDirection()];
                break;
            case BODY$:
                img = BS$[dataUnit.getDirection()];
                break;
            case MOUSE:
                img = MS[getMouseDirection(dataUnit.getDirection())];
                break;
        }
        return img;
    }

    private static int getMouseDirection(int d) {
        if (d == 2 || d == 3 || d == 4 || d == 7) {
            return 0;
        } else {
            return 1;
        }
    }


    public static int getSnakeBodyDirection(int oldD, int d) {
        switch (oldD) {
            case 0:
                if (d == 1) {
                    return 2;
                } else {
                    return 3;
                }
            case 1:
                if (d == 0) {
                    return 0;
                } else {
                    return 3;
                }
            case 2:
                if (d == 1) {
                    return 1;
                } else {
                    return 0;
                }
            case 3:
                if (d == 0) {
                    return 1;
                } else {
                    return 2;
                }
        }
        return -1;
    }

    public static int getSnakeTailDirection(int d, int b) {
        switch (d) {
            case 0:
                if (b == 2) {
                    return 1;
                } else {
                    return 3;
                }
            case 1:
                if (b == 3) {
                    return 2;
                } else {
                    return 0;
                }
            case 2:
                if (b == 0) {
                    return 3;
                } else {
                    return 1;
                }
            case 3:
                if (b == 1) {
                    return 0;
                } else {
                    return 2;
                }
        }
        return -1;
    }
}

package universe.img;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Administrator on 2016/4/12.
 */
public class IMG {
    static int length = 4;
    public static Image[] HS = new Image[length];
    public static Image[] BS= new Image[length];
    public static Image[] BS$= new Image[length];
    public static Image[] TS= new Image[length];
    static {
        for (int i = 0; i < length; i++) {
            HS[i] = getImageAt("./images/snake/head" + i + ".jpg");
            BS[i] = getImageAt("./images/snake/body" + i + ".jpg");
            BS$[i] = getImageAt("./images/snake/body$" + i + ".jpg");
            TS[i] = getImageAt("./images/snake/tail" + i + ".jpg");
        }
    }
    public static Image getImageAt(String parent, String imgName) {
        return new ImageIcon(parent + imgName).getImage();
    }
    public static Image getImageAt(String imgName) {
        return new ImageIcon(imgName).getImage();
    }


    public static int getDirection$(int oldD, int d) {
        switch (oldD) {
            case 0:
                if (d == 1) {
                    return 1;
                } else {
                    return 2;
                }
            case 1:
                if (d == 0) {
                    return 3;
                } else {
                    return 2;
                }
            case 2:
                if (d == 1) {
                    return 0;
                } else {
                    return 3;
                }
            case 3:
                if (d == 0) {
                    return 0;
                } else {
                    return 1;
                }
        }
        return -1;
    }

    public static int getDirection$0(int d, int b) {
        switch (d) {
            case 0:
                if (b == 1) {
                    return 1;
                } else {
                    return 3;
                }
            case 1:
                if (b == 2) {
                    return 2;
                } else {
                    return 0;
                }
            case 2:
                if (b == 0) {
                    return 1;
                } else {
                    return 3;
                }
            case 3:
                if (b == 1) {
                    return 2;
                } else {
                    return 0;
                }
        }
        return -1;
    }
}

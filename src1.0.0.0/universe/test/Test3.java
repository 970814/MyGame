package universe.test;

import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/4/18.
 */
public class Test3 {
    public static void main(String[] args) throws IOException {

        File file = new File("./编码.txt");
        FileInputStream stream = new FileInputStream(file);
        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] b = new byte[60];
        int pn = -1;
        int length = 0;
        do {
            pn = stream.read(b);
            for (int i = 0; i < pn; i++) {
                bytes.add(b[i]);
            }
        } while (pn > 0);

        byte[] data = new byte[bytes.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = bytes.get(i);
        }
        System.out.println(new String(data, "gb2312"));
        System.out.println(new String(data, "utf8"));
        System.out.println(new String(data, "utf16"));
        System.out.println(new String(data, "unicode"));
        GraphicsEnvironment.getLocalGraphicsEnvironment();
    }
}

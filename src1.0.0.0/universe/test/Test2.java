package universe.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/4/18.
 */
public class Test2 {
    public static void main(String[] args) throws FileNotFoundException {
        int[] a = {83, 84, 79, 80,};
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader(new File(""));
        fileReader.getEncoding();
        int where = Arrays.binarySearch(a, 79);
        System.out.println(where);
    }
}

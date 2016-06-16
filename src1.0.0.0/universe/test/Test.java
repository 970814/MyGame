package universe.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/12.
 */
public class Test {
    public static void main(String[] args) throws IOException {
//        String file = "c:\\test\\t\\tasd\\tsavdsgsd\\tssta\\";
//        makeFile(file);
//        deleteFile("c:\\test");

        String src = "c:\\test\\src\\1.txt";
        String des = "c:\\test\\des\\";
//        makeFile(src);
//        makeFile(des);
        fileCopy(src, des);
    }

    public static void fileCopy(String src, String des) throws IOException {
        File srcFile = new File(src);
        File desFile = new File(des);
        if (!srcFile.isFile()) {
            System.out.println("不是一个文件: " + src);
            return;
        }
        if (!desFile.isDirectory()) {
            System.out.println("不是一个文件夹: " + des);
            return;
        }
        if (!desFile.exists()) {
            desFile.mkdirs();
        }
        File objFile = new File(desFile, srcFile.getName());
        makeFile(objFile.getPath());
        FileInputStream in = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(objFile);
        int aByte;
        while ((aByte = in.read()) != -1) {
            out.write(aByte);
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File f : files) {
                    deleteFile(f.getPath());
                }
            }
            System.out.println((file.delete() ? "成功删除文件夹: " : "无法删除文件夹: ") + "\'" + file.getName() + "\'");
        } else {
            System.out.println((file.delete() ? "成功删除文件: " : "无法删除文件: ") + "\'" + file.getName() + "\'");
        }
    }

    public static void makeFile(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();

//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.getAbsoluteFile());
//        System.out.println(file.getCanonicalPath());
//        System.out.println(file.getCanonicalFile());
//        System.out.println(file.getPath());
//        System.out.println(file.getName());
//        System.out.println(file.getParent());
//        System.out.println(file.getParentFile());

        if (!parent.exists()) {
            if (parent.mkdirs()) {
                System.out.println("成功创建文件: " + "\'" + parent + "\'");
            } else {
                System.out.println("创建文件失败: " + "\'" + parent + "\'");
            }
        } else {
            System.out.println("已经存在文件: " + "\'" + parent + "\'");
        }


        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("成功创建文件: " + "\'" + file.getName() + "\'");
            } else {
                System.out.println("创建文件失败: " + "\'" + file.getName() + "\'");
            }
        } else {
            System.out.println("已经存在文件: " + "\'" + parent + "\'");

        }
    }
}

package test;

import java.awt.*;
import java.awt.event.*;

public class DoubleBuffer extends Frame//主类继承Frame类
{
    public paintThread pT;//绘图线程
    public int ypos = -80; //小圆左上角的纵坐标

    public DoubleBuffer()//构造函数
    {
        pT = new paintThread(this);
        this.setResizable(false);
        this.setSize(300, 300); //设置窗口的首选大小
        this.setVisible(true); //显示窗口
        pT.start();//绘图线程启动
    }
    private Image iBuffer;
    private Graphics gBuffer;

    //重载paint(Graphics scr)函数：
    public void paint(Graphics scr) {
        if (iBuffer == null) {
            iBuffer = this.createImage(this.getSize().width, this.getSize().height);
            gBuffer = iBuffer.getGraphics();
        }
        /**
         * gBuffer 得到的是对iBuffer图像绘制的绘制器
         * gBuffer 绘制缓冲图片
         */
        gBuffer.setColor(getBackground());
        gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
        gBuffer.setColor(Color.RED);
        gBuffer.fillOval(90, ypos, 80, 80);
        scr.drawImage(iBuffer, 0, 0, this);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public static void main(String[] args) {
        DoubleBuffer DB = new DoubleBuffer();//创建主类的对象
        DB.addWindowListener(new WindowAdapter()//添加窗口关闭处理函数
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
class paintThread extends Thread//绘图线程类
{
    DoubleBuffer DB;
    public paintThread(DoubleBuffer DB) //构造函数
    {
        this.DB=DB;
    }

    public void run()//重载run()函数
    {
        while (true)//线程中的无限循环
        {
            try {
                sleep(10); //线程休眠30ms
            } catch (InterruptedException e) {
            }
            DB.ypos += 5; //修改小圆左上角的纵坐标
            if (DB.ypos > 300) //小圆离开窗口后重设左上角的纵坐标
                DB.ypos = -80;
//            DB.repaint();//窗口重绘
            DB.repaint();
        }
    }
}


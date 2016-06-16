package mapper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Administrator on 2016/4/25.
 */
@SuppressWarnings("serial")
public class Monitor extends JFrame implements Runnable {
    private int fps = 30;
    private final Painter painter;
    private final JTextArea textPane;
    boolean isFocus = false;

    public Monitor(Painter painter) {
//        setLayout(new GridLayout(0, 2));
        this.painter = painter;

        painter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
        textPane = new JTextArea() {
            {
                setFont(new Font("Times New Roman", Font.BOLD, 30));
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.PINK,1, true), "Idle"));
                setLineWrap(true);
            }
        };
//        add(textPane);
        add(painter);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setAlwaysOnTop(true);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (isFocus) {
                    return;
                }
                Component component = getComponentAt(e.getPoint());
                if (!component.isFocusOwner()) {
                    component.requestFocus();
                    isFocus = true;
                }

            }
        };
        getRootPane().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true), "Busying..."));
                painter.setBorder(BorderFactory.createLineBorder(Color.PINK, 3, true));
                painter.getDataModel().workAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                painter.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
                isFocus = false;
                TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.PINK, 1, true), "Idle");
                border.setTitleFont(new Font("Times New Roman", Font.BOLD, 30));
                textPane.setBorder(border);
                painter.getDataModel().sleepAll();
            }
        });
        getRootPane().addMouseListener(mouseAdapter);
        setExtendedState(MAXIMIZED_BOTH);
    }

    @Override
    public void setVisible(boolean arg0) {
        super.setVisible(arg0);
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(fps);
                repaint();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addControllerListener(KeyListener keyListener) {
        getRootPane().addKeyListener(keyListener);
    }
}

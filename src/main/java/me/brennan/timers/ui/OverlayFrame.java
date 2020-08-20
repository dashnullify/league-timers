package me.brennan.timers.ui;

import me.brennan.timers.objects.Champion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 8/20/2020
 **/
public class OverlayFrame extends JFrame {

    public OverlayFrame(List<Champion> champions) {
        super("OverlayFrame");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = screenSize.getWidth();
        Double height = screenSize.getHeight();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(width.intValue(), height.intValue());
        setPreferredSize(new Dimension(width.intValue(), height.intValue()));
        setAlwaysOnTop(true);
        addWindowListener(getWindowAdapter());

        getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

        OverlayPanel panel = new OverlayPanel(champions);
        panel.setOpaque(false);
        add(panel);

        setVisible(true);
        pack();

    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent we) {
                setState(JFrame.NORMAL);
            }
        };
    }
}

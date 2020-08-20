package me.brennan.timers.ui;

import me.brennan.timers.objects.Champion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 8/20/2020
 **/
public class OverlayPanel extends JPanel  {
    private Font font;
    private final List<Champion> champions;

    public OverlayPanel(List<Champion> champions) {
        font = new Font("Serif", Font.BOLD, 16);
        this.champions = champions;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString("LOL Timers", 6, 15);
        int y = 28;
        for(Champion champion : champions) {
            graphics.drawString(champion.getName(), 6, y);
            y += 14;
        }

        graphics.drawRect(3, 0, 100, 100);
    }

}

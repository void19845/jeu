package Main;

import Entity.player;
import javax.swing.*;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable {
    keyhandler kh = new keyhandler();

    final int Default = 32;
    final int Scale = 4;
    // screen size & resolution
    public final int tileSize = Default * Scale;
    final int MaxScrenColum = Default;
    final int MaxScrenRow = Default / 5;
    final int ScreenWidth = tileSize * MaxScrenColum;
    final int ScreenHeight = tileSize * MaxScrenRow;
    //framerate
    int fps = 60;
    //fonction importante
    Thread gameThread;
    player player= new player(this,kh);


    public gamePanel() {//constructeur
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(kh);
    }//constructeur

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double frameInterval = 1000.0 / fps;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long now;
        while (gameThread != (null)) {//it's the main loop of our game where we run it
            now = System.currentTimeMillis();
            delta += (now - lastTime) / frameInterval;
            lastTime = now;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }


        }
    }

    public void update() {
    player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
        g2d.dispose();
    }
}

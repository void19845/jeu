package Main;

import java.awt.*;

public class Mob {
    public int worldX, worldY;
    public int size = 32;
    public int speed = 2;
    public int dirX = 1, dirY = 0;
    public int life = 100;
    public boolean alive = true;

    public Mob(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }

    public Rectangle getHitbox() {
        return new Rectangle(worldX, worldY, size, size);
    }

    public void draw(Graphics2D g2, int camX, int camY) {
        if (!alive) return;
        g2.setColor(Color.MAGENTA);
        g2.fillRect(worldX - camX, worldY - camY, size, size);
    }
}

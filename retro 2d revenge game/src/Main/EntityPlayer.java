package Main;

import java.awt.*;

public class EntityPlayer {
    public int worldX, worldY;
    public int size = 32;
    public int speed = 4;
    public int life = 100;
    public boolean alive = true;
    public boolean immune = false;
    public long lastHit = 0;

    public EntityPlayer(int startX, int startY) {
        this.worldX = startX;
        this.worldY = startY;
    }

    public Rectangle getHitbox() {
        return new Rectangle(worldX, worldY, size, size);
    }

    public void hit(int fromX, int fromY) {
        if (immune) return;
        life -= 1;
        worldX += -fromX * 20;
        worldY += -fromY * 20;
        lastHit = System.currentTimeMillis();
        immune = true;
        if (life <= 0) alive = false;
    }
}

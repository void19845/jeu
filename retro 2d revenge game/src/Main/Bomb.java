package Main;

import java.awt.*;

public class Bomb {
    public int x, y;
    public int targetX, targetY;
    public boolean active = true;
    public boolean exploding = false;
    public long arrivalTime = 0;
    public int speed = 8;
    public static final int BLAST_RADIUS = 64;

    public Bomb(int startX, int startY, int targetX, int targetY) {
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void update() {
        if (exploding) {
            if (System.currentTimeMillis() - arrivalTime >= 2000) {
                active = false;
            }
            return;
        }

        double dx = targetX - x;
        double dy = targetY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist <= speed) {
            x = targetX;
            y = targetY;
            exploding = true;
            arrivalTime = System.currentTimeMillis();
        } else {
            x += (int) (speed * dx / dist);
            y += (int) (speed * dy / dist);
        }
    }

    public void draw(Graphics2D g2, int camX, int camY) {
        if (exploding) {
            g2.setColor(Color.ORANGE);
            g2.fillOval(x - BLAST_RADIUS - camX, y - BLAST_RADIUS - camY, BLAST_RADIUS * 2, BLAST_RADIUS * 2);
        } else {
            g2.setColor(Color.YELLOW);
            g2.fillOval(x - camX, y - camY, 16, 16);
        }
    }

    public boolean isInBlast(int px, int py) {
        int dx = px - x;
        int dy = py - y;
        return dx * dx + dy * dy <= BLAST_RADIUS * BLAST_RADIUS;
    }
}

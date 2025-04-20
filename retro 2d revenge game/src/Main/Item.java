package Main;

import java.awt.*;

public class Item {
    public int worldX, worldY;
    public boolean pickedUp = false;

    public Item(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }

    public Rectangle getHitbox() {
        return new Rectangle(worldX, worldY, 28, 28);
    }

    public void draw(Graphics2D g2, int offsetX, int offsetY) {
        if (!pickedUp) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(worldX - offsetX, worldY - offsetY, 28, 28);
        }
    }
}

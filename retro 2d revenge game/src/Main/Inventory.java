package Main;

import java.awt.*;
import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> items = new ArrayList<>();

    public void add(Item item) {
        items.add(item);
    }

    public void draw(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, screenHeight - 100, screenWidth, 100);
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 9; i++) {
            g2.drawRect(10 + i * 90, screenHeight - 90, 80, 80);
        }
        for (int i = 0; i < items.size() && i < 9; i++) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(20 + i * 90, screenHeight - 80, 60, 60);
        }
    }
}

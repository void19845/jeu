package Main;

import java.io.Serializable;

public class GameData implements Serializable {
    public int playerX;
    public int playerY;

    public GameData(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }
}

package Main;

public class World {
    public static final int TILE_SIZE = 32;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    public static int[][] map = new int[HEIGHT][WIDTH];

    static {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x == 0 || y == 0 || x == WIDTH - 1 || y == HEIGHT - 1 || (x % 10 == 0 && y % 8 == 0)) {
                    map[y][x] = 1;
                } else {
                    map[y][x] = 0;
                }
            }
        }
    }

    public static boolean isWall(int x, int y) {
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) return true;
        return map[y][x] == 1;
    }
}

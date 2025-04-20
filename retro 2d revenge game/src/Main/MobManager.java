package Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MobManager {
    public ArrayList<Mob> mobs = new ArrayList<>();
    EntityPlayer player;
    Random rand = new Random();

    public MobManager(EntityPlayer player) {
        this.player = player;
        for (int i = 0; i < 5; i++) {
            int x = 32 * (10 + rand.nextInt(80));
            int y = 32 * (10 + rand.nextInt(80));
            mobs.add(new Mob(x, y));
        }
    }

    public void update() {
        for (Mob mob : mobs) {
            if (!mob.alive) continue;

            int dx = player.worldX - mob.worldX;
            int dy = player.worldY - mob.worldY;
            int dist = Math.abs(dx) + Math.abs(dy);

            if (dist < 300) {
                mob.dirX = Integer.compare(dx, 0);
                mob.dirY = Integer.compare(dy, 0);
            } else {
                if (rand.nextInt(60) == 0) {
                    mob.dirX = rand.nextInt(3) - 1;
                    mob.dirY = rand.nextInt(3) - 1;
                }
            }

            int nextX = mob.worldX + mob.dirX * mob.speed;
            int nextY = mob.worldY + mob.dirY * mob.speed;
            int tileX = nextX / World.TILE_SIZE;
            int tileY = nextY / World.TILE_SIZE;

            if (!World.isWall(tileX, tileY)) {
                mob.worldX = nextX;
                mob.worldY = nextY;
            }

            if (mob.getHitbox().intersects(player.getHitbox())) {
                player.hit(mob.dirX, mob.dirY);
                mob.worldX -= mob.dirX * 20;
                mob.worldY -= mob.dirY * 20;
            }
        }
    }

    public void draw(Graphics2D g2, int camX, int camY) {
        for (Mob mob : mobs) mob.draw(g2, camX, camY);
    }
}

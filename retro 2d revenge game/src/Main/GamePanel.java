package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    Thread gameThread;
    int screenWidth = 1280;
    int screenHeight = 720;

    EntityPlayer player;
    Inventory inventory = new Inventory();
    MobManager mobManager;
    ArrayList<Item> worldItems = new ArrayList<>();
    ArrayList<Bomb> bombs = new ArrayList<>();

    boolean up, down, left, right, pickup;
    boolean paused = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.setFocusable(true);
        player = new EntityPlayer(100, 100);
        mobManager = new MobManager(player);
        initItems();
    }

    public GamePanel(GameData data) {
        this();
        player = new EntityPlayer(data.playerX, data.playerY);
        mobManager = new MobManager(player);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initItems() {
        worldItems.add(new Item(6 * 32, 6 * 32));
        worldItems.add(new Item(10 * 32, 8 * 32));
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / 60;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                if (!paused && player.alive) update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (player.immune && System.currentTimeMillis() - player.lastHit >= 2000) {
            player.immune = false;
        }

        int nextX = player.worldX;
        int nextY = player.worldY;
        if (up) nextY -= player.speed;
        if (down) nextY += player.speed;
        if (left) nextX -= player.speed;
        if (right) nextX += player.speed;

        int tileX = nextX / World.TILE_SIZE;
        int tileY = nextY / World.TILE_SIZE;
        if (!World.isWall(tileX, tileY)) {
            player.worldX = nextX;
            player.worldY = nextY;
        }

        for (Item item : worldItems) {
            if (!item.pickedUp && player.getHitbox().intersects(item.getHitbox())) {
                if (pickup) {
                    item.pickedUp = true;
                    inventory.add(item);
                }
            }
        }

        ArrayList<Bomb> toRemove = new ArrayList<>();
        for (Bomb bomb : bombs) {
            bomb.update();
            if (bomb.exploding) {
                for (Mob mob : mobManager.mobs) {
                    if (mob.alive && bomb.isInBlast(mob.worldX, mob.worldY)) {
                        mob.alive = false;
                    }
                }
            }
            if (!bomb.active) toRemove.add(bomb);
        }
        bombs.removeAll(toRemove);

        mobManager.update();
        pickup = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int camX = player.worldX - screenWidth / 2 + player.size / 2;
        int camY = player.worldY - screenHeight / 2 + player.size / 2;

        for (int y = 0; y < World.HEIGHT; y++) {
            for (int x = 0; x < World.WIDTH; x++) {
                int wx = x * World.TILE_SIZE - camX;
                int wy = y * World.TILE_SIZE - camY;
                if (World.map[y][x] == 1) {
                    g2.setColor(Color.GRAY);
                    g2.fillRect(wx, wy, World.TILE_SIZE, World.TILE_SIZE);
                } else {
                    g2.setColor(Color.DARK_GRAY);
                    g2.fillRect(wx, wy, World.TILE_SIZE, World.TILE_SIZE);
                }
            }
        }

        for (Item item : worldItems) item.draw(g2, camX, camY);
        for (Bomb bomb : bombs) bomb.draw(g2, camX, camY);
        mobManager.draw(g2, camX, camY);

        g2.setColor(Color.RED);
        g2.fillRect(screenWidth / 2 - player.size / 2, screenHeight / 2 - player.size / 2, player.size, player.size);

        inventory.draw(g2, screenWidth, screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Consolas", Font.BOLD, 18));
        g2.drawString("ZQSD: Déplacement | E: Ramasser | Clic: Bombe | P: Sauvegarder | ESC: Pause", 20, 30);
        g2.drawString("Vie: " + player.life, screenWidth - 150, 30);

        if (paused && player.alive) {
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            g2.drawString("Jeu en pause", screenWidth / 2 - 120, screenHeight / 2 - 100);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            g2.drawString("[R] Reprendre", screenWidth / 2 - 100, screenHeight / 2);
            g2.drawString("[S] Sauvegarder & Quitter", screenWidth / 2 - 100, screenHeight / 2 + 40);
            g2.drawString("[Q] Quitter", screenWidth / 2 - 100, screenHeight / 2 + 80);
        }

        if (!player.alive) {
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 64));
            g2.drawString("GAME OVER", screenWidth / 2 - 200, screenHeight / 2);
        }
    }

    public GameData getGameData() {
        return new GameData(player.worldX, player.worldY);
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        if (paused) {
            if (c == KeyEvent.VK_R) paused = false;
            if (c == KeyEvent.VK_S) {
                GameSave.save(getGameData());
                System.exit(0);
            }
            if (c == KeyEvent.VK_Q) System.exit(0);
            return;
        }

        if (!player.alive) return;

        if (c == KeyEvent.VK_Z) up = true;
        if (c == KeyEvent.VK_S) down = true;
        if (c == KeyEvent.VK_Q) left = true;
        if (c == KeyEvent.VK_D) right = true;
        if (c == KeyEvent.VK_E) pickup = true;
        if (c == KeyEvent.VK_P) GameSave.save(getGameData());
        if (c == KeyEvent.VK_ESCAPE) paused = true;
    }

    @Override public void keyReleased(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_Z) up = false;
        if (c == KeyEvent.VK_S) down = false;
        if (c == KeyEvent.VK_Q) left = false;
        if (c == KeyEvent.VK_D) right = false;
    }

    @Override public void mouseClicked(MouseEvent e) {
        if (!player.alive || paused) return;
        if (inventory.items.size() > 0) {
            inventory.items.remove(0);
            int clickX = e.getX() + player.worldX - screenWidth / 2;
            int clickY = e.getY() + player.worldY - screenHeight / 2;
            bombs.add(new Bomb(player.worldX, player.worldY, clickX, clickY));
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}

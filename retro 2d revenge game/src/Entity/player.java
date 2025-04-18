package Entity;
import Main.keyhandler;
import Main.gamePanel;
import java.awt.*;

public class player extends Entity{
    gamePanel gp;
    keyhandler kh;
    int Score;
    int playerSpeed;


    public player(gamePanel gp, keyhandler kh) {//constructeur
        this.gp = gp;
        this.kh = kh;
        this.Score = 0;
        this.playerSpeed=1;
        setDefaultValues();
    }//constructeur

    public void setDefaultValues() {//player variable based on passed variable from entity
        Y = 500;
        X = 500;
        baseSpeed = 5;
    }//player variable based on passed variable from entity

    public void update() {//update the position
        movementJoueur();
    }//update the position

    public void movementJoueur() {//move the player
        updatespeed();
        if (kh.downPressed) {
            Y += playerSpeed;
        } else if (kh.upPressed) {
            Y -= playerSpeed;
        }
        if (kh.leftPressed) {
            X -= playerSpeed;
        } else if (kh.rightPressed) {
            X += playerSpeed;
        }
    }//move the player

    private void updatespeed() {//set the speed of the player based on acceleration and modifier
        playerSpeed=baseSpeed;
    }//set the speed of the player based on acceleration and modifier

    public void draw(Graphics2D g2d) {//draw the player
        g2d.setColor(Color.white);
        g2d.fillRect(X, Y,gp.tileSize,gp.tileSize);
    }//draw the player
}

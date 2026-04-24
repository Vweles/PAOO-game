package PaooGame.Characters;
import PaooGame.KeyImput.InputTaste; // Verifică dacă pachetul e cu 'm' sau 'n'
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import PaooGame.Physics.CollisionChecker;

public class MC extends Entity {
    private InputTaste keyInput;


    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 12;

    public MC(String NUME, int x, int y, InputTaste keyInput) {

        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;

        solidArea = new Rectangle(14, 32, 16,11);
    }

    public void update(CollisionChecker Checker) {
        boolean isMoving = false;

        if (keyInput.crawl || !limbsStatus[R_LEG] || !limbsStatus[L_LEG]) {
            this.speed = 1;
        } else {

            this.speed = 1 + agi / 15;
        }


        if (keyInput.up) { directie = "up"; isMoving = true; }
        else if (keyInput.down) { directie = "down"; isMoving = true; }
        else if (keyInput.left) { directie = "left"; isMoving = true; }
        else if (keyInput.right) { directie = "right"; isMoving = true; }

        collisionOn = false;
        if(isMoving)
        {
            Checker.checkTile(this);
        }
        if (!collisionOn && isMoving) {
            switch (directie) {
                case "up": y -= speed; break;
                case "down": y += speed; break;
                case "left": x -= speed; break;
                case "right": x += speed; break;
            }
        }


        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;


                int maxFrames = keyInput.crawl ? 6 : 11;
                if (animIndex >= maxFrames) {
                    animIndex = 0;
                }
            }
        } else {

            animIndex = 0;
        }


    }

    public void draw(Graphics g) {
        BufferedImage currentFrame = null;


        if (keyInput.crawl) {
            switch (directie) {
                case "up":
                    currentFrame = Assets.crawlUp[animIndex % 6];
                    break;
                case "down":
                    currentFrame = Assets.crawlDown[animIndex % 6];
                    break;
                case "left":
                    currentFrame = Assets.crawlLeft[animIndex % 6];
                    break;
                case "right":
                    currentFrame = Assets.crawlRight[animIndex % 6];
                    break;
            }
        } else {
            switch (directie) {
                case "up":
                    currentFrame = Assets.walkUp[animIndex % 11];
                    break;
                case "down":
                    currentFrame = Assets.walkDown[animIndex % 11];
                    break;
                case "left":
                    currentFrame = Assets.walkLeft[animIndex % 11];
                    break;
                case "right":
                    currentFrame = Assets.walkRight[animIndex % 11];
                    break;
            }
        }


        if (currentFrame != null) {
            // Desenam curat, doar la x si y-ul jucatorului
            g.drawImage(currentFrame, x, y, 48, 48, null);
        }
    }
}
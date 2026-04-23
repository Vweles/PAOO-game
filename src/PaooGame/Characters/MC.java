package PaooGame.Characters;

import PaooGame.KeyImput.InputTaste; // Verifică dacă pachetul e cu 'm' sau 'n'
import PaooGame.Graphics.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MC extends Entity {
    private InputTaste keyInput;
    public String directie = "down";


    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 6;

    public MC(String NUME, int x, int y, InputTaste keyInput) {

        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
    }

    public void update() {
        boolean isMoving = false;

        if (keyInput.crawl || !limbsStatus[R_LEG] || !limbsStatus[L_LEG]) {
            this.speed = 1;
        } else {

            this.speed = 2 + agi / 5;
        }


        if (keyInput.up) {
            directie = "up";
            y -= speed;
            isMoving = true;
        } else if (keyInput.down) {
            directie = "down";
            y += speed;
            isMoving = true;
        }

        if (keyInput.left) {
            directie = "left";
            x -= speed;
            isMoving = true;
        } else if (keyInput.right) {
            directie = "right";
            x += speed;
            isMoving = true;
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
                case "up":    currentFrame = Assets.crawlUp[animIndex % 6];    break;
                case "down":  currentFrame = Assets.crawlDown[animIndex % 6];  break;
                case "left":  currentFrame = Assets.crawlLeft[animIndex % 6];  break;
                case "right": currentFrame = Assets.crawlRight[animIndex % 6]; break;
            }
        } else {
            switch (directie) {
                case "up":    currentFrame = Assets.walkUp[animIndex % 11];    break;
                case "down":  currentFrame = Assets.walkDown[animIndex % 11];  break;
                case "left":  currentFrame = Assets.walkLeft[animIndex % 11];  break;
                case "right": currentFrame = Assets.walkRight[animIndex % 11]; break;
            }
        }


        if (currentFrame != null) {
            g.drawImage(currentFrame, x, y, 48, 48, null);
        }
    }
}
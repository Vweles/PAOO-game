package PaooGame.Characters;

import PaooGame.KeyImput.InputTaste;
import PaooGame.Graphics.Assets;
import PaooGame.Physics.CollisionChecker;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MC extends Entity {
    private InputTaste keyInput;
    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 6;

    public MC(String NUME, int x, int y, InputTaste keyInput) {
        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;

        solidArea = new Rectangle(8, 16, 32, 32);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;


        if (keyInput.up) { directie = "up"; isMoving = true; }
        else if (keyInput.down) { directie = "down"; isMoving = true; }
        else if (keyInput.left) { directie = "left"; isMoving = true; }
        else if (keyInput.right) { directie = "right"; isMoving = true; }

        // nu mai merge nica din ce ai facut am facut cea mai mare prostie nu mai schimb in viata mea si nu mai adaug nica in viata mea
        collisionOn = false;
        if (isMoving) {
            checker.checkTile(this);

            // deci cred ca distrus tot ce ai facut
            if (!collisionOn) {
                switch (directie) {
                    case "up":    y -= speed; break;
                    case "down":  y += speed; break;
                    case "left":  x -= speed; break;
                    case "right": x += speed; break;
                }
            }
        }

        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;
                int maxFrames = keyInput.crawl ? 6 : 11;
                if (animIndex >= maxFrames) animIndex = 0;
            }
        } else {
            animIndex = 0;
        }

        if (keyInput.crawl || !limbsStatus[R_LEG] || !limbsStatus[L_LEG]) this.speed = 1;
        else this.speed = 2 + agi / 5;
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame;
        if (keyInput.crawl) {
            switch (directie) {
                case "up": frame = Assets.crawlUp[animIndex % 6]; break;
                case "left": frame = Assets.crawlLeft[animIndex % 6]; break;
                case "right": frame = Assets.crawlRight[animIndex % 6]; break;
                default: frame = Assets.crawlDown[animIndex % 6]; break;
            }
        } else {
            switch (directie) {
                case "up": frame = Assets.walkUp[animIndex % 11]; break;
                case "left": frame = Assets.walkLeft[animIndex % 11]; break;
                case "right": frame = Assets.walkRight[animIndex % 11]; break;
                default: frame = Assets.walkDown[animIndex % 11]; break;
            }
        }
        g.drawImage(frame, x, y, 48, 48, null);
    }
}
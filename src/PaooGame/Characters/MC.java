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
    private final int ANIM_SPEED = 10;

    public MC(String NUME, int x, int y, InputTaste keyInput) {
        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
        this.solidArea = new Rectangle(12, 20, 24, 24);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;

        // Setam directia si verificam daca se misca
        if (keyInput.up) { directie = "up"; isMoving = true; }
        else if (keyInput.down) { directie = "down"; isMoving = true; }
        else if (keyInput.left) { directie = "left"; isMoving = true; }
        else if (keyInput.right) { directie = "right"; isMoving = true; }

        collisionOn = false;
        // Folosim checker-ul primit ca parametru in metoda update
        checker.checkTile(this);

        if (!collisionOn && isMoving) {
            if (directie.equals("up")) y -= speed;
            if (directie.equals("down")) y += speed;
            if (directie.equals("left")) x -= speed;
            if (directie.equals("right")) x += speed;
        }

        // Animatie
        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;
                int maxFrames = keyInput.crawl ? 2 : 3;
                if (animIndex >= maxFrames) animIndex = 0;
            }
        } else {
            animIndex = 0;
        }

        // Viteza
        if (keyInput.crawl || !limbsStatus[R_LEG] || !limbsStatus[L_LEG]) {
            this.speed = 1;
        } else {
            this.speed = 2 + agi / 5;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame = null;
        if (keyInput.crawl) {
            switch (directie) {
                case "up": frame = Assets.crawlUp[animIndex % 2]; break;
                case "down": frame = Assets.crawlDown[animIndex % 2]; break;
                case "left": frame = Assets.crawlLeft[animIndex % 2]; break;
                case "right": frame = Assets.crawlRight[animIndex % 2]; break;
                default: frame = Assets.crawlDown[0]; break;
            }
        } else {
            switch (directie) {
                case "up": frame = Assets.walkUp[animIndex % 3]; break;
                case "down": frame = Assets.walkDown[animIndex % 3]; break;
                case "left": frame = Assets.walkLeft[animIndex % 3]; break;
                case "right": frame = Assets.walkRight[animIndex % 3]; break;
                default: frame = Assets.walkDown[0]; break;
            }
        }

        if (frame != null) {
            g.drawImage(frame, (int)x, (int)y, 48, 48, null);
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
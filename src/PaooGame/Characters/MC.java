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
    private final int ANIM_SPEED = 12;

    public MC(String NUME, int x, int y, InputTaste keyInput) {
        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
        this.solidArea = new Rectangle(12, 24, 24, 20);
    }
    public float getX() { return x; }
    public float getY() { return y; }

    // REPARAT: Numele metodei trebuie să fie EXACT "update" (cu 'u' mic)
    // dacă așa este în clasa Entity, și trebuie să aibă @Override
    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;

        if (keyInput.up) { directie = "up"; isMoving = true; }
        else if (keyInput.down) { directie = "down"; isMoving = true; }
        else if (keyInput.left) { directie = "left"; isMoving = true; }
        else if (keyInput.right) { directie = "right"; isMoving = true; }

        this.speed = (keyInput.crawl) ? 1 : 3;

        collisionOn = false;
        checker.checkTile(this);

        if (!collisionOn && isMoving) {
            if (directie.equals("up")) y -= speed;
            if (directie.equals("down")) y += speed;
            if (directie.equals("left")) x -= speed;
            if (directie.equals("right")) x += speed;
        }

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
    }

    @Override
    public void draw(Graphics g) {
        // Implementare de bază cerută de Entity
    }

    // Metoda ta custom de desenare cu Camera
    public void Draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage frame = null;
        if (keyInput.crawl) {
            if (directie.equals("up")) frame = Assets.crawlUp[animIndex % 2];
            else if (directie.equals("down")) frame = Assets.crawlDown[animIndex % 2];
            else if (directie.equals("left")) frame = Assets.crawlLeft[animIndex % 2];
            else if (directie.equals("right")) frame = Assets.crawlRight[animIndex % 2];
        } else {
            if (directie.equals("up")) frame = Assets.walkUp[animIndex % 3];
            else if (directie.equals("down")) frame = Assets.walkDown[animIndex % 3];
            else if (directie.equals("left")) frame = Assets.walkLeft[animIndex % 3];
            else if (directie.equals("right")) frame = Assets.walkRight[animIndex % 3];
        }

        if (frame != null) {
            g.drawImage(frame, (int)x - cameraX, (int)y - cameraY, 48, 48, null);
        }
    }
}
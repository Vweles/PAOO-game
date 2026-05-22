package PaooGame.Characters;

import PaooGame.KeyImput.InputTaste;
import PaooGame.Graphics.Assets;
import PaooGame.Physics.CollisionChecker;
import PaooGame.Items.Inventory;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MC extends Entity {
    private final InputTaste keyInput;
    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 12;
    private final Inventory inventory;

    public MC(String nume, int x, int y, InputTaste keyInput) {

        super(nume, 80, 10, 60, 60, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
        this.inventory = new Inventory(this);


        this.getSolidArea().setBounds(12, 24, 24, 20);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;


        setCollisionOn(false);


        if (keyInput.up) { setDirectie("up"); isMoving = true; }
        else if (keyInput.down) { setDirectie("down"); isMoving = true; }
        else if (keyInput.left) { setDirectie("left"); isMoving = true; }
        else if (keyInput.right) { setDirectie("right"); isMoving = true; }


        this.setSpeed(keyInput.crawl ? 1 : 3);


        if (isMoving) {
            checker.checkTile(this);
        }


        if (!isCollisionOn() && isMoving) {
            switch (getDirectie()) {
                case "up":    setY(getY() - getSpeed()); break;
                case "down":  setY(getY() + getSpeed()); break;
                case "left":  setX(getX() - getSpeed()); break;
                case "right": setX(getX() + getSpeed()); break;
            }
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
    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage frame = null;


        if (keyInput.crawl) {
            if (getDirectie().equals("up")) frame = Assets.crawlUp[animIndex % 2];
            else if (getDirectie().equals("down")) frame = Assets.crawlDown[animIndex % 2];
            else if (getDirectie().equals("left")) frame = Assets.crawlLeft[animIndex % 2];
            else if (getDirectie().equals("right")) frame = Assets.crawlRight[animIndex % 2];
        } else {
            if (getDirectie().equals("up")) frame = Assets.walkUp[animIndex % 3];
            else if (getDirectie().equals("down")) frame = Assets.walkDown[animIndex % 3];
            else if (getDirectie().equals("left")) frame = Assets.walkLeft[animIndex % 3];
            else if (getDirectie().equals("right")) frame = Assets.walkRight[animIndex % 3];
        }

        if (frame != null) {

            int drawX = getX() - cameraX;
            int drawY = getY() - cameraY;
            g.drawImage(frame, drawX, drawY, 48, 48, null);
        }
    }



    public Inventory getInventory() {
        return inventory;
    }
    @Override
    public void performTurn(MC player) {

    }


}
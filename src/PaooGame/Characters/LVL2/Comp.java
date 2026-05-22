package PaooGame.Characters.LVL2;

import PaooGame.Characters.Entity;
import PaooGame.Characters.Npc;
import PaooGame.Graphics.Assets;
import PaooGame.Physics.CollisionChecker;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Comp extends Npc {

    private Entity target;
    private int animIndex = 0;
    private int speedCounter = 0;
    private final int ANIM_SPEED = 12;
    private final int DISTANTA_STOP = 50;

    public Comp(String NUME, int x, int y, Entity player) {
        super(NUME, 50, 50, 50, 50, 4, 2, 1, x, y, 3, true, true, false, "Guardian", true);
        this.target = player;
        this.getSolidArea().setBounds(12, 24, 24, 20);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;
        float diffX = target.getX() - this.getX();
        float diffY = target.getY() - this.getY();
        double distanta = Math.sqrt(diffX * diffX + diffY * diffY);

        if (distanta > DISTANTA_STOP) {
            isMoving = true;
            if (Math.abs(diffX) > Math.abs(diffY)) {
                this.setDirectie((diffX > 0) ? "right" : "left");
            } else {
                this.setDirectie((diffY > 0) ? "down" : "up");
            }

            this.setCollisionOn(false);
            checker.checkTile(this);

            if (!this.isCollisionOn()) {
                if (this.getDirectie().equals("up")) this.setY(this.getY() - this.getSpeed());
                if (this.getDirectie().equals("down")) this.setY(this.getY() + this.getSpeed());
                if (this.getDirectie().equals("left")) this.setX(this.getX() - this.getSpeed());
                if (this.getDirectie().equals("right")) this.setX(this.getX() + this.getSpeed());
            }
        }

        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;
                if (animIndex >= 3) animIndex = 0;
            }
        } else {
            animIndex = 0;
        }
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage frame = null;
        if (this.getDirectie().equals("up")) frame = Assets.walkUp[animIndex % 3];
        else if (this.getDirectie().equals("down")) frame = Assets.walkDown[animIndex % 3];
        else if (this.getDirectie().equals("left")) frame = Assets.walkLeft[animIndex % 3];
        else if (this.getDirectie().equals("right")) frame = Assets.walkRight[animIndex % 3];

        if (frame != null) {
            g.drawImage(frame, this.getX() - cameraX, this.getY() - cameraY, 48, 48, null);
            g.setColor(Color.WHITE);
            g.drawString(this.getNume(), this.getX() - cameraX + 5, this.getY() - cameraY - 5);
        }
    }
}
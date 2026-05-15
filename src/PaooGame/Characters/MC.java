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
    private PaooGame.Items.Inventory inventory;

    public MC(String NUME, int x, int y, InputTaste keyInput) {
        super(NUME, 70, 10, 60, 60, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
        this.inventory = new PaooGame.Items.Inventory(this);
        // Definirea zonei de coliziune relative la sprite-ul de 48x48
        this.solidArea = new Rectangle(12, 24, 24, 20);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;

        // Preluare input direcție
        if (keyInput.up) { directie = "up"; isMoving = true; }
        else if (keyInput.down) { directie = "down"; isMoving = true; }
        else if (keyInput.left) { directie = "left"; isMoving = true; }
        else if (keyInput.right) { directie = "right"; isMoving = true; }

        // Setare viteză în funcție de stare (Crawl vs Walk)
        this.speed = (keyInput.crawl) ? 1 : 3;

        // Verificare coliziuni
        collisionOn = false;
        checker.checkTile(this);

        // Deplasare dacă nu există coliziune
        if (!collisionOn && isMoving) {
            if (directie.equals("up")) y -= speed;
            if (directie.equals("down")) y += speed;
            if (directie.equals("left")) x -= speed;
            if (directie.equals("right")) x += speed;
        }

        // Logică animație
        if (isMoving) {
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;
                int maxFrames = keyInput.crawl ? 2 : 3;
                if (animIndex >= maxFrames) animIndex = 0;
            }
        } else {
            animIndex = 0; // Reset la cadrul de repaus
        }
    }

    // ##########################################
    // SEMN: REPARAT SEMNATURA PENTRU A CORESPUNDE CU ENTITY
    // Acum folosim @Override pe metoda draw cu parametri de camera
    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        BufferedImage frame = null;

        // Selectie frame in functie de stare (Crawl/Walk) si Directie
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

        // Desenare efectiva
        if (frame != null) {
            g.drawImage(frame, (int)x - cameraX, (int)y - cameraY, 48, 48, null);
        }
    }
    // ##########################################
    public PaooGame.Items.Inventory getInventory() { return inventory; }
}
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
    private final int ANIM_SPEED = 10; // Am marit putin delay-ul pentru un târât mai natural

    public MC(String NUME, int x, int y, InputTaste keyInput) {
        super(NUME, 12, 10, 15, 10, 4, 2, 1, x, y, 4);
        this.keyInput = keyInput;
        // Ajustam zona de coliziune sa fie mai mica, utila la târât
        solidArea = new Rectangle(12, 20, 24, 24);
    }

    @Override
    public void update(CollisionChecker checker) {
        boolean isMoving = false;

        // --- LOGICA DE DIRECTIE SI MISCARE ---
        // Folosim if-else if pentru a preveni "spam-ul" de directii
        // (daca apesi W si S deodata, va merge doar in sus)
        if (keyInput.up) {
            directie = "up";
            isMoving = true;
        } else if (keyInput.down) {
            directie = "down";
            isMoving = true;
        } else if (keyInput.left) {
            directie = "left";
            isMoving = true;
        } else if (keyInput.right) {
            directie = "right";
            isMoving = true;
        }

        collisionOn = false;
        if (isMoving) {
            checker.checkTile(this);

            if (!collisionOn) {
                switch (directie) {
                    case "up":    y -= speed; break;
                    case "down":  y += speed; break;
                    case "left":  x -= speed; break;
                    case "right": x += speed; break;
                }
            }

            // --- LOGICA DE ANIMATIE ---
            speedCounter++;
            if (speedCounter >= ANIM_SPEED) {
                animIndex++;
                speedCounter = 0;

                // Setam numarul de cadre in functie de stare (Crawl are 2, Walk are 3)
                int maxFrames = keyInput.crawl ? 2 : 3;
                if (animIndex >= maxFrames) animIndex = 0;
            }
        } else {
            // Daca nu se misca, ramane pe primul cadru (repaus)
            animIndex = 0;
        }

        // --- VITEZA ---
        // Daca se târăște sau are picioarele rupte, viteza este minima
        if (keyInput.crawl || !limbsStatus[R_LEG] || !limbsStatus[L_LEG]) {
            this.speed = 1;
        } else {
            this.speed = 2 + agi / 5;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame = null;

        // Verificam mai intai daca suntem in starea de Crawl
        if (keyInput.crawl) {
            // Aici imaginea depinde STRICT de directie (Sus/Jos/Stanga/Dreapta)
            switch (directie) {
                case "up":    frame = Assets.crawlUp[animIndex % 2]; break;
                case "down":  frame = Assets.crawlDown[animIndex % 2]; break;
                case "left":  frame = Assets.crawlLeft[animIndex % 2]; break;
                case "right": frame = Assets.crawlRight[animIndex % 2]; break;
                default:      frame = Assets.crawlDown[0]; break;
            }
        } else {
            // Mers normal
            switch (directie) {
                case "up":    frame = Assets.walkUp[animIndex % 3]; break;
                case "down":  frame = Assets.walkDown[animIndex % 3]; break;
                case "left":  frame = Assets.walkLeft[animIndex % 3]; break;
                case "right": frame = Assets.walkRight[animIndex % 3]; break;
                default:      frame = Assets.walkDown[0]; break;
            }
        }

        // Desenam cadrul corespunzator
        if (frame != null) {
            g.drawImage(frame, x, y, 48, 48, null);
        }
    }
}
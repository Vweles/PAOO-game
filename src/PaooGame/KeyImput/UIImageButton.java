package PaooGame.KeyImput;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class UIImageButton {
    private int x, y, width, height;
    private BufferedImage[] images;
    private Rectangle bounds;
    private boolean hovering = false; // Aceasta variabila e privata
    private Runnable onClick;

    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.images = images;
        this.onClick = onClick;

        bounds = new Rectangle(x, y, width, height);
    }

    // ### REPARATIE: Am adaugat aceasta metoda ###
    // Aceasta metoda permite altor clase (ca BattleState) sa vada daca mouse-ul e peste buton
    public boolean isHovering() {
        return hovering;
    }

    public void Update(int mouseX, int mouseY, boolean leftPressed) {
        // Verificam daca mouse-ul este in interiorul dreptunghiului butonului
        hovering = (bounds != null && bounds.contains(mouseX, mouseY));

        // Daca dam click in timp ce suntem peste buton, executam actiunea
        if (hovering && leftPressed && onClick != null) {
            onClick.run();
        }
    }

    public void Draw(Graphics g) {
        // Daca nu am incarcat imagini (Assets sunt null), nu desenam nimic
        // sau am putea desena un dreptunghi de test.
        if (images == null || images[0] == null) return;

        if (hovering) {
            g.drawImage(images[1], x, y, width, height, null);
        } else {
            g.drawImage(images[0], x, y, width, height, null);
        }
    }
}
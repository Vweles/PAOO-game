package PaooGame.KeyImput;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class UIImageButton {
    private int x, y, width, height;
    private BufferedImage[] images;
    private Rectangle bounds;
    private boolean hovering = false;
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

    public void Update(int mouseX, int mouseY, boolean leftPressed) {
        // Verificam daca mouse-ul este in "hitBox"
        hovering = (bounds != null && bounds.contains(mouseX, mouseY));

        if (hovering && leftPressed && onClick != null) {
            onClick.run();
        }
    }

    public void Draw(Graphics g) {
        if (images == null) return;

        if (hovering) {
            g.drawImage(images[1], x, y, width, height, null);
        } else {
            g.drawImage(images[0], x, y, width, height, null);
        }
    }
}
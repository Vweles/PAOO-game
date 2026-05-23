package PaooGame.KeyImput;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class UIImageButton {
    private int x, y, width, height;
    private BufferedImage[] images;
    private Rectangle bounds;
    private boolean hovering = false;
    private Runnable onClick;
    private String text = "";


    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.images = images;
        this.onClick = onClick;

        bounds = new Rectangle(x, y, width, height);
    }


    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images, String text, Runnable onClick) {
        this(x, y, width, height, images, onClick);
        this.text = text;
    }

    public boolean isHovering() {
        return hovering;
    }

    public void Update(int mouseX, int mouseY, boolean leftPressed) {
        hovering = (bounds != null && bounds.contains(mouseX, mouseY));

        if (hovering && leftPressed && onClick != null) {
            onClick.run();
        }
    }

    public void Draw(Graphics g) {

        if (images != null && images[0] != null) {
            if (hovering && images.length > 1 && images[1] != null) {
                g.drawImage(images[1], x, y, width, height, null);
            } else {
                g.drawImage(images[0], x, y, width, height, null);
            }
        }


        if (text != null && !text.isEmpty()) {
            g.setFont(new Font("Arial", Font.BOLD, 22));


            g.setColor(hovering ? Color.RED : Color.WHITE);

            FontMetrics fm = g.getFontMetrics();
            int textX = x + (width - fm.stringWidth(text)) / 2;
            int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();

            g.drawString(text, textX, textY);
        }
    }
}
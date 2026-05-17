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
    private String text = ""; // ADĂUGAT: Salvează textul butonului

    // Constructorul original (rămâne pentru compatibilitate în caz că e folosit în altă parte)
    public UIImageButton(int x, int y, int width, int height, BufferedImage[] images, Runnable onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.images = images;
        this.onClick = onClick;

        bounds = new Rectangle(x, y, width, height);
    }

    // ADĂUGAT: Constructor nou care acceptă și textul dinamic
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
        // Desenează fundalul grafic de piatră din Butoane2.png
        if (images != null && images[0] != null) {
            if (hovering && images.length > 1 && images[1] != null) {
                g.drawImage(images[1], x, y, width, height, null);
            } else {
                g.drawImage(images[0], x, y, width, height, null);
            }
        }

        // ADĂUGAT: Desenează textul aliniat perfect pe centrul butonului
        if (text != null && !text.isEmpty()) {
            g.setFont(new Font("Arial", Font.BOLD, 22));

            // Schimbă culoarea textului în funcție de starea mouse-ului (Roșu pe hover, Alb în rest)
            g.setColor(hovering ? Color.RED : Color.WHITE);

            FontMetrics fm = g.getFontMetrics();
            int textX = x + (width - fm.stringWidth(text)) / 2;
            int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();

            g.drawString(text, textX, textY);
        }
    }
}
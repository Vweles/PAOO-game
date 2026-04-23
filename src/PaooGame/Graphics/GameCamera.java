package PaooGame.Graphics;

import PaooGame.Characters.Entity;

public class GameCamera {
    private float xOffset, yOffset;
    private int windowWidth, windowHeight;
    private float zoom = 2.0f;

    public GameCamera(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        xOffset = 0;
        yOffset = 0;
    }

    public void centerOnEntity(Entity e) {
        // Calculăm unde ar trebui să fie camera pentru a centra entitatea
        float targetX = e.x * zoom - (windowWidth / 2f) + (24 * zoom);
        float targetY = e.y * zoom - (windowHeight / 2f) + (24 * zoom);

        // Smoothing: camera nu se teleportează, ci "alunecă" spre țintă (0.1f = viteza)
        xOffset += (targetX - xOffset) * 0.1f;
        yOffset += (targetY - yOffset) * 0.1f;
    }

    public void checkBlankSpace(int mapWidthPixels, int mapHeightPixels) {
        if (xOffset < 0) xOffset = 0;
        if (yOffset < 0) yOffset = 0;
        if (xOffset > mapWidthPixels * zoom - windowWidth) xOffset = mapWidthPixels * zoom - windowWidth;
        if (yOffset > mapHeightPixels * zoom - windowHeight) yOffset = mapHeightPixels * zoom - windowHeight;
    }

    public float getxOffset() { return xOffset; }
    public float getyOffset() { return yOffset; }
    public float getZoom() { return zoom; }
}
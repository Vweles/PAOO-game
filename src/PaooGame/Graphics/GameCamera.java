package PaooGame.Graphics;

import PaooGame.Characters.MC;

public class GameCamera {
    private float xOffset, yOffset;
    private float zoom = 1.0f;
    private int wndWidth, wndHeight;

    public GameCamera(int wndWidth, int wndHeight) {
        this.wndWidth = wndWidth;
        this.wndHeight = wndHeight;
    }

    public void centerOnEntity(MC e) {
        // Centrare pe erou
        xOffset = e.getX() - (wndWidth / (2 * zoom)) + 16;
        yOffset = e.getY() - (wndHeight / (2 * zoom)) + 16;
    }

    public void checkBlankSpace(int worldWidth, int worldHeight) {
        if (xOffset < 0) xOffset = 0;
        if (yOffset < 0) yOffset = 0;
        if (xOffset > worldWidth - (wndWidth / zoom)) xOffset = worldWidth - (wndWidth / zoom);
        if (yOffset > worldHeight - (wndHeight / zoom)) yOffset = worldHeight - (wndHeight / zoom);
    }

    public void setZoom(float zoom) { this.zoom = zoom; }
    public float getZoom() { return zoom; }
    public float getxOffset() { return xOffset; }
    public float getyOffset() { return yOffset; }
}
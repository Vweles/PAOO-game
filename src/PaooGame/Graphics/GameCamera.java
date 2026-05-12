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

        xOffset = e.getX() - (wndWidth / (2 * zoom)) + 16;
        yOffset = e.getY() - (wndHeight / (2 * zoom)) + 16;
    }

    public void checkBlankSpace(int worldWidth, int worldHeight) {
        // Calculăm limitele luând în calcul zoom-ul
        float viewWidth = wndWidth / zoom;
        float viewHeight = wndHeight / zoom;

        // Constrângere la stânga și sus
        if (xOffset < 0) xOffset = 0;
        if (yOffset < 0) yOffset = 0;

        // Constrângere la dreapta și jos (nu lăsa camera să vadă peste marginea hărții)
        if (xOffset > worldWidth - viewWidth) {
            xOffset = worldWidth - viewWidth;
        }
        if (yOffset > worldHeight - viewHeight) {
            yOffset = worldHeight - viewHeight;
        }

        // Safety check: dacă harta e mai mică decât fereastra, centreaz-o pe 0
        if(worldWidth < viewWidth) xOffset = 0;
        if(worldHeight < viewHeight) yOffset = 0;
    }

    public void setZoom(float zoom) { this.zoom = zoom; }
    public float getZoom() { return zoom; }
    public float getxOffset() { return xOffset; }
    public float getyOffset() { return yOffset; }
}
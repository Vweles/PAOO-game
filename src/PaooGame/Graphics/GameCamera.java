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

        float entityCenterX = e.getX() + (e.getSolidArea().width / 2.0f);
        float entityCenterY = e.getY() + (e.getSolidArea().height / 2.0f);

        xOffset = entityCenterX - (wndWidth / (2 * zoom));
        yOffset = entityCenterY - (wndHeight / (2 * zoom));
    }
    public void checkBlankSpace(int worldWidth, int worldHeight) {

        float viewWidth = wndWidth / zoom;
        float viewHeight = wndHeight / zoom;


        if (xOffset < 0) xOffset = 0;
        if (yOffset < 0) yOffset = 0;


        if (xOffset > worldWidth - viewWidth) {
            xOffset = worldWidth - viewWidth;
        }
        if (yOffset > worldHeight - viewHeight) {
            yOffset = worldHeight - viewHeight;
        }


        if(worldWidth < viewWidth) xOffset = 0;
        if(worldHeight < viewHeight) yOffset = 0;
    }

    public void setZoom(float zoom) { this.zoom = zoom; }
    public float getZoom() { return zoom; }
    public float getxOffset() { return xOffset; }
    public float getyOffset() { return yOffset; }
}
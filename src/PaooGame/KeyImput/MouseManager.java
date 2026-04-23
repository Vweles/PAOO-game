package PaooGame.KeyImput;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

    public boolean leftPressed, rightPressed;
    public int mouseX, mouseY;

    public MouseManager() { }

    // Metode pentru miscare
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Se updateaza coordonatele si cand tii apasat si tragi
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // Metode pentru click
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = true;  // Click Stanga
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = true; // Click Dreapta
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = false;
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = false;
    }

    // Nu le folosim momentan, dar trebuie declarate
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}
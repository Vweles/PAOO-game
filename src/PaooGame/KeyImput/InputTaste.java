package PaooGame.KeyImput;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputTaste implements KeyListener {


    public boolean up, down, left, right, attack, crawl, enter;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) up = true;
        if (code == KeyEvent.VK_S) down = true;
        if (code == KeyEvent.VK_A) left = true;
        if (code == KeyEvent.VK_D) right = true;


        if (code == KeyEvent.VK_K) attack = true;


        if (code == KeyEvent.VK_C) {
            crawl = !crawl;
            System.out.println("Crawl state: " + crawl);
        }
        if (code == KeyEvent.VK_ENTER) enter = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) up = false;
        if (code == KeyEvent.VK_S) down = false;
        if (code == KeyEvent.VK_A) left = false;
        if (code == KeyEvent.VK_D) right = false;
        if (code == KeyEvent.VK_K) attack = false;
        if (code == KeyEvent.VK_ENTER) enter = false;
    }

    public boolean isEnterPressed() {
        if (enter) {
            enter = false;
            return true;
        }
        return false;
    }
}
package PaooGame.KeyImput;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputTaste implements KeyListener {


    public boolean up, down, left, right, attack, crawl, enter;
    public boolean esc;
    public boolean s_key, l_key;
    public boolean inv, h_pot, m_pot;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(e.getKeyCode() == KeyEvent.VK_S) s_key = true;
        if(e.getKeyCode() == KeyEvent.VK_L) l_key = true;

        if (code == KeyEvent.VK_W) up = true;
        if (code == KeyEvent.VK_S) down = true;
        if (code == KeyEvent.VK_A) left = true;
        if (code == KeyEvent.VK_D) right = true;


        if (code == KeyEvent.VK_K) attack = true;
        if (code == KeyEvent.VK_ESCAPE) esc = true;


        if (code == KeyEvent.VK_I) inv = true;
        if (code == KeyEvent.VK_H) h_pot = true;
        if (code == KeyEvent.VK_M) m_pot = true;


        if (code == KeyEvent.VK_C) {
            crawl = !crawl;
            System.out.println("Crawl state: " + crawl);
        }
        if (code == KeyEvent.VK_ENTER) enter = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(e.getKeyCode() == KeyEvent.VK_S) s_key = false;
        if(e.getKeyCode() == KeyEvent.VK_L) l_key = false;

        if (code == KeyEvent.VK_W) up = false;
        if (code == KeyEvent.VK_S) down = false;
        if (code == KeyEvent.VK_A) left = false;
        if (code == KeyEvent.VK_D) right = false;
        if (code == KeyEvent.VK_K) attack = false;
        if (code == KeyEvent.VK_ENTER) enter = false;


        if (code == KeyEvent.VK_ESCAPE) esc = false;

        if (code == KeyEvent.VK_I) inv = false;
        if (code == KeyEvent.VK_H) h_pot = false;
        if (code == KeyEvent.VK_M) m_pot = false;
    }

    public boolean isEnterPressed() {
        if (enter) {
            enter = false;
            return true;
        }
        return false;
    }
}
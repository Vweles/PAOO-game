package PaooGame.KeyImput;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputTaste implements KeyListener {

    public boolean up, down, left, right, attack, crawl;

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
        //o sa mai agaugam si pt
        //drink potiune
        //interact cu cv

        // vezi ca la asta tre sa fie gen atack adica cu  cv magie fireball nebunii de astea
        if (code == KeyEvent.VK_K) attack = true;

        // am   pe spite sheeiit si de astea de crawl o sa fie folosite sa dea el crawl sau daca esti dragut si mai incolo adaugi cand isi pierde picioarele
        // sau un picior da crawl
        if (code == KeyEvent.VK_C) {
            crawl = !crawl;
            System.out.println("Crawl state: " + crawl);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) up = false;
        if (code == KeyEvent.VK_S) down = false;
        if (code == KeyEvent.VK_A) left = false;
        if (code == KeyEvent.VK_D) right = false;
        if (code == KeyEvent.VK_K) attack = false;

    }
}
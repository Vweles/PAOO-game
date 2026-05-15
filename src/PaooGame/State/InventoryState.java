package PaooGame.State;

import PaooGame.Characters.MC;
import PaooGame.Items.Iteme;
import java.awt.*;
import java.util.ArrayList;

public class InventoryState extends State {
    private PlayState backState;
    private Iteme selectedItem = null;
    private int selectedIndex = -1;


    private Rectangle headSlot = new Rectangle(380, 80, 40, 40);
    private Rectangle cloakSlot = new Rectangle(330, 130, 40, 40);
    private Rectangle chestSlot = new Rectangle(380, 130, 40, 40);
    private Rectangle handSlot = new Rectangle(430, 130, 40, 40);
    private Rectangle ringSlot = new Rectangle(475, 130, 30, 30);
    private Rectangle bootsSlot = new Rectangle(380, 185, 40, 40);


    private int startX = 100, startY = 320, slotSize = 40, spacing = 8;
    private Rectangle equipButton = new Rectangle(0, 0, 0, 0);

    public InventoryState(PlayState backState) {
        this.backState = backState;
    }

    @Override
    public void Update() {
        if (backState.getKeyInput().inv) {
            State.SetState(backState);
            backState.getKeyInput().inv = false;
        }


        if (backState.getMouseManager().isLeftPressed()) {
            int mx = backState.getMouseManager().getMouseX();
            int my = backState.getMouseManager().getMouseY();


            ArrayList<Iteme> bag = backState.getPlayer().getInventory().bag;
            for (int i = 0; i < bag.size(); i++) {
                int col = i % 12;
                int row = i / 12;
                int x = startX + col * (slotSize + spacing);
                int y = startY + row * (slotSize + spacing);
                Rectangle r = new Rectangle(x, y, slotSize, slotSize);

                if (r.contains(mx, my)) {
                    selectedItem = bag.get(i);
                    selectedIndex = i;
                }
            }


            if (selectedItem != null && equipButton.contains(mx, my)) {
                backState.getPlayer().getInventory().equip(selectedItem);
                backState.getPlayer().getInventory().bag.remove(selectedIndex);
                selectedItem = null;
                selectedIndex = -1;
            }


            backState.getMouseManager().setLeftPressed(false);
        }
    }

    @Override
    public void Draw(Graphics g) {
        backState.Draw(g);
        g.setColor(new Color(0, 0, 0, 225));
        g.fillRect(50, 40, 700, 520);

        MC p = backState.getPlayer();


        drawEquippedItem(g, headSlot, p.getInventory().head, "CAP");
        drawEquippedItem(g, cloakSlot, p.getInventory().cloak, "CAPA");
        drawEquippedItem(g, chestSlot, p.getInventory().chest, "CORP");
        drawEquippedItem(g, handSlot, p.getInventory().hands, "MANA");
        drawEquippedItem(g, ringSlot, p.getInventory().ring, "INEL");
        drawEquippedItem(g, bootsSlot, p.getInventory().boots, "BOOTS");


        ArrayList<Iteme> bag = p.getInventory().bag;
        for (int i = 0; i < 48; i++) {
            int col = i % 12;
            int row = i / 12;
            int x = startX + col * (slotSize + spacing);
            int y = startY + row * (slotSize + spacing);

            g.setColor(Color.DARK_GRAY);
            g.drawRect(x, y, slotSize, slotSize);

            if (i < bag.size()) {
                g.drawImage(bag.get(i).texture, x, y, slotSize, slotSize, null);
                if (bag.get(i) == selectedItem) {
                    g.setColor(Color.YELLOW);
                    g.drawRect(x-1, y-1, slotSize+2, slotSize+2);
                }
            }
        }


        if (selectedItem != null) {
            int tx = 540, ty = 320;
            g.setColor(new Color(40, 40, 45));
            g.fillRect(tx, ty, 200, 160);
            g.setColor(Color.WHITE);
            g.drawRect(tx, ty, 200, 160);

            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(selectedItem.name, tx + 10, ty + 25);

            g.setFont(new Font("Arial", Font.PLAIN, 12));
            g.setColor(Color.GREEN);
            int py = ty + 50;
            if(selectedItem.bonusStr > 0) { g.drawString("+ " + selectedItem.bonusStr + " STR", tx + 10, py); py+=15; }
            if(selectedItem.bonusInte > 0) { g.drawString("+ " + selectedItem.bonusInte + " INT", tx + 10, py); py+=15; }
            if(selectedItem.bonusAgi > 0) { g.drawString("+ " + selectedItem.bonusAgi + " AGI", tx + 10, py); py+=15; }
            if(selectedItem.bonusTho > 0) { g.drawString("+ " + selectedItem.bonusTho + " THO", tx + 10, py); py+=15; }


            equipButton = new Rectangle(tx + 50, ty + 120, 100, 30);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(equipButton.x, equipButton.y, equipButton.width, equipButton.height);
            g.setColor(Color.WHITE);
            g.drawRect(equipButton.x, equipButton.y, equipButton.width, equipButton.height);
            g.drawString("EQUIP", equipButton.x + 30, equipButton.y + 20);
        }


        drawPlayerStats(g, p);
    }

    private void drawPlayerStats(Graphics g, MC p) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("STATS TOTALE:", 580, 80);
        g.drawString("STR: " + p.str, 580, 105);
        g.drawString("INT: " + p.inte, 580, 130);
        g.drawString("AGI: " + p.agi, 580, 155);
        g.drawString("THO: " + p.tho, 580, 180);
    }

    private void drawEquippedItem(Graphics g, Rectangle r, Iteme item, String label) {
        g.setColor(Color.WHITE);
        g.drawRect(r.x, r.y, r.width, r.height);
        if (item != null) {
            g.drawImage(item.texture, r.x, r.y, r.width, r.height, null);
        } else {
            g.setFont(new Font("Arial", Font.PLAIN, 10));
            g.drawString(label, r.x, r.y - 5);
        }
    }
}
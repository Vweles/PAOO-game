package PaooGame.State;

import PaooGame.KeyImput.InputTaste;
import PaooGame.KeyImput.MouseManager;
import java.awt.*;

public class ObiectivState extends State {

    private PlayState playState;
    private InputTaste keyInput;
    private MouseManager mouseManager;
    private int nivelCurent;


    private String povesteText = "";
    private String obiectivText = "";

    public ObiectivState(PlayState playState, InputTaste keyInput, MouseManager mouseManager, int nivelCurent) {
        this.playState = playState;
        this.keyInput = keyInput;
        this.mouseManager = mouseManager;
        this.nivelCurent = nivelCurent;


        setareTexteObiective();
    }

    private void setareTexteObiective() {
        switch (nivelCurent) {
            case 1:
                povesteText = "Ai ajuns in abisul intunecat, un loc parasit de zei unde putregaiul "
                        + "si monstrii domnesc. Singura cale de scapare este sa gasesti "
                        + "si sa distrugi Heraldul Putreziciunii care pazeste portalul.";
                obiectivText = "OBIECTIV: OMOARA BOSSUL MORBRYX!";
                break;

            case 2:
                povesteText = "Flacarile Infernului iti parjolesc armura. Fortele boss-ului "
                        + "sunt prea bine Blindate. Pentru a-i slabi defensiva si a-l face "
                        + "sa iasa din ascunzatoare, trebuie sa cureti zona de acolitii sai.";
                obiectivText = "OBIECTIV: ELIMINA  BOSSUL NIVELULUI!";
                break;

            case 3:
                povesteText = "Te afli in ultima faza a calatoriei tale, in fata zeului impostor. "
                        + "Emilian si campionul sau au control total asupra acestei lumi. "
                        + "Foloseste tot ce ai invatat si pune capat acestei tiranii!";
                obiectivText = "OBIECTIV FINAL: INVINGE CAMPIONUL LUI EMILIAN!";
                break;

            default:
                povesteText = "TEXTUL TAU AICI - Poveste generica pentru nivele secrete sau viitoare.";
                obiectivText = "OBIECTIV: TEXTUL TAU BOLD AICI";
                break;
        }
    }

    @Override
    public void Update() {

        if (keyInput.o_tasta || keyInput.esc) {
            keyInput.o_tasta = false;
            keyInput.esc = false;
            State.SetState(playState);
        }
    }

    @Override
    public void Draw(Graphics g) {

        playState.Draw(g);


        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, playState.getCurrentMap().getWidth() * 32, playState.getCurrentMap().getHeight() * 32);


        int casetaW = 500;
        int casetaH = 300;
        int casetaX = (800 - casetaW) / 2;
        int casetaY = (600 - casetaH) / 2;


        g.setColor(new Color(30, 30, 30, 240));
        g.fillRect(casetaX, casetaY, casetaW, casetaH);

        g.setColor(new Color(255, 215, 0));
        g.drawRect(casetaX, casetaY, casetaW, casetaH);
        g.drawRect(casetaX + 3, casetaY + 3, casetaW - 6, casetaH - 6);


        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("NIVELUL " + nivelCurent + " - OBIECTIVE", casetaX + 30, casetaY + 40);


        g.setColor(Color.GRAY);
        g.drawLine(casetaX + 30, casetaY + 55, casetaX + casetaW - 30, casetaY + 55);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));

        int textX = casetaX + 30;
        int textY = casetaY + 90;


        String[] cuvinte = povesteText.split(" ");
        StringBuilder linieCurenta = new StringBuilder();

        for (String cuvant : cuvinte) {
            if (linieCurenta.length() + cuvant.length() > 55) {
                g.drawString(linieCurenta.toString(), textX, textY);
                textY += 22;
                linieCurenta = new StringBuilder();
            }
            linieCurenta.append(cuvant).append(" ");
        }
        if (linieCurenta.length() > 0) {
            g.drawString(linieCurenta.toString(), textX, textY);
        }


        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(new Color(255, 69, 0)); // Portocaliu aprins / Roșiatic
        g.drawString(obiectivText, casetaX + 30, casetaY + casetaH - 60);


        g.setFont(new Font("Arial", Font.ITALIC, 11));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Apasa [ O ] sau [ ESC ] pentru a incepe jocul...", casetaX + 30, casetaY + casetaH - 25);
    }
}
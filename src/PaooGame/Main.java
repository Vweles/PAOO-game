package PaooGame;

import PaooGame.Database.DatabaseManager;
import PaooGame.GameWindow.GameWindow;

public class Main
{



    public static void main(String[] args)
    {
        DatabaseManager.init();
        Game paooGame = new Game("PaooGame",800,600);
        paooGame.StartGame();
    }
}

package PaooGame.Tiles;
import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/*! \class public class Tile
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 256;
    public static Tile[] tiles          = new Tile[NO_TILES];       /*!< Vector de referinte de tipuri de dale.*/

        /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
        /// o singura data in memorie
    public static Tile grassTile        = new GrassTile(0);     /*!< Dala de tip iarba*/
    //public static Tile mountainTile     = new MountainTile(1);  /*!< Dala de tip munte/piatra*/
    //public static Tile waterTile        = new WaterTile(2);     /*!< Dala de tip apa*/
    //public static Tile treeTile         = new TreeTile(3);      /*!< Dala de tip copac*/
    //public static Tile soilTile         = new SoilTile(4);      /*!< Dala de tip sol/pamant*/
    public static  Tile ground = new WorldTile(Assets.Tile1,2,false);
    public static Tile Wall = new WorldTile(Assets.Wall,6,true);
    public static Tile Abis = new WorldTile(Assets.Abis,1,true);
    public static final int TILE_WIDTH  = 32;                       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 32;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/

    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /*! \fn public void Update()
        \brief Actualizeaza proprietatile dalei.
     */
    public void Update()
    {

    }

    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */

    public void Draw(Graphics g, int x, int y) {

        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /*! \fn public boolean IsSolid()
        \brief Returneaza proprietatea de dala solida (supusa coliziunilor) sau nu.
     */

    // Modifica in Tile.java sau in clasele derivate (WallTile, WorldTile)
    public boolean IsSolid() {
        // Daca ID-ul este 6 (Wall in TMJ-ul tau) sau 1 (Abis), returneaza true
        if (this.id == 6 || this.id == 1) {
            return true;
        }
        return false;
    }

    /*! \fn public int GetId()
        \brief Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }
}

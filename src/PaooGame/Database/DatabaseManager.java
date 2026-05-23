package PaooGame.Database;

import PaooGame.Items.Iteme;
import PaooGame.Items.ItemGenerator;
import PaooGame.State.PlayState;
import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:paoo_game_data.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL); Statement stmt = conn.createStatement()) {


            stmt.execute("CREATE TABLE IF NOT EXISTS savegame (" +
                    "slot_id INTEGER PRIMARY KEY, " +
                    "hp INTEGER, mana INTEGER, " +
                    "x REAL, y REAL, " +
                    "level INTEGER, " +
                    "state_type TEXT)");


            stmt.execute("CREATE TABLE IF NOT EXISTS inventory (" +
                    "slot_id INTEGER, " +
                    "location TEXT, " +
                    "item_name TEXT, " +
                    "item_type TEXT, " +
                    "bonus_str INTEGER, " +
                    "bonus_inte INTEGER, " +
                    "bonus_agi INTEGER, " +
                    "bonus_tho INTEGER)");

        } catch (SQLException e) {
            System.err.println("Eroare la crearea tabelelor!");
            e.printStackTrace();
        }
    }


    private static void insertItemToDB(PreparedStatement pstmt, int slotId, String location, Iteme item) throws SQLException {
        if (item == null) return;
        pstmt.setInt(1, slotId);
        pstmt.setString(2, location);
        pstmt.setString(3, item.name);
        pstmt.setString(4, item.type.toString());
        pstmt.setInt(5, item.bonusStr);
        pstmt.setInt(6, item.bonusInte);
        pstmt.setInt(7, item.bonusAgi);
        pstmt.setInt(8, item.bonusTho);
        pstmt.addBatch();
    }

    public static synchronized void save(int slotId, PlayState ps) {
        String sqlGame = "INSERT OR REPLACE INTO savegame (slot_id, hp, mana, x, y, level, state_type) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlClearInv = "DELETE FROM inventory WHERE slot_id = ?";
        String sqlInv = "INSERT INTO inventory (slot_id, location, item_name, item_type, bonus_str, bonus_inte, bonus_agi, bonus_tho) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL)) {

            conn.setAutoCommit(false);


            try (PreparedStatement pstmt = conn.prepareStatement(sqlGame)) {
                pstmt.setInt(1, slotId);
                pstmt.setInt(2, ps.getPlayer().getHp());
                pstmt.setInt(3, ps.getPlayer().getMana());
                pstmt.setFloat(4, ps.getPlayer().getX());
                pstmt.setFloat(5, ps.getPlayer().getY());
                pstmt.setInt(6, ps.getCurrentLevelNo());
                pstmt.setString(7, ps.getClass().getSimpleName());
                pstmt.executeUpdate();
            }


            try (PreparedStatement pstmtClear = conn.prepareStatement(sqlClearInv)) {
                pstmtClear.setInt(1, slotId);
                pstmtClear.executeUpdate();
            }


            try (PreparedStatement pstmtInv = conn.prepareStatement(sqlInv)) {
                PaooGame.Items.Inventory inv = ps.getPlayer().getInventory();


                for (Iteme i : inv.bag) {
                    insertItemToDB(pstmtInv, slotId, "BAG", i);
                }


                insertItemToDB(pstmtInv, slotId, "HEAD", inv.head);
                insertItemToDB(pstmtInv, slotId, "CHEST", inv.chest);
                insertItemToDB(pstmtInv, slotId, "HANDS", inv.hands);
                insertItemToDB(pstmtInv, slotId, "BOOTS", inv.boots);
                insertItemToDB(pstmtInv, slotId, "CLOAK", inv.cloak);
                insertItemToDB(pstmtInv, slotId, "RING", inv.ring);


                for (int i = 0; i < inv.artifacts.length; i++) {
                    insertItemToDB(pstmtInv, slotId, "ARTIFACT_" + i, inv.artifacts[i]);
                }

                pstmtInv.executeBatch();
            }

            conn.commit();
            System.out.println("Salvare TOTALĂ de nota 10 reușită pentru slotul: " + slotId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void load(int slotId, PlayState ps) {
        String sqlGame = "SELECT * FROM savegame WHERE slot_id = ?";
        String sqlInv = "SELECT * FROM inventory WHERE slot_id = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {

            try (PreparedStatement pstmtGame = conn.prepareStatement(sqlGame)) {
                pstmtGame.setInt(1, slotId);
                ResultSet rsGame = pstmtGame.executeQuery();

                if (rsGame.next()) {
                    ps.loadGameData(
                            rsGame.getInt("hp"),
                            rsGame.getInt("mana"),
                            rsGame.getFloat("x"),
                            rsGame.getFloat("y"),
                            rsGame.getInt("level")
                    );
                } else {
                    System.out.println("Nu s-a găsit nicio salvare pe slotul " + slotId);
                    return;
                }
            }


            PaooGame.Items.Inventory inv = ps.getPlayer().getInventory();


            inv.bag.clear();
            inv.head = null; inv.chest = null; inv.hands = null;
            inv.boots = null; inv.cloak = null; inv.ring = null;
            for (int i = 0; i < inv.artifacts.length; i++) {
                inv.artifacts[i] = null;
            }

            try (PreparedStatement pstmtInv = conn.prepareStatement(sqlInv)) {
                pstmtInv.setInt(1, slotId);
                ResultSet rsInv = pstmtInv.executeQuery();

                while (rsInv.next()) {
                    String loc = rsInv.getString("location");
                    String name = rsInv.getString("item_name");
                    String typeStr = rsInv.getString("item_type");
                    int bStr = rsInv.getInt("bonus_str");
                    int bInte = rsInv.getInt("bonus_inte");
                    int bAgi = rsInv.getInt("bonus_agi");
                    int bTho = rsInv.getInt("bonus_tho");

                    Iteme.ItemType type = Iteme.ItemType.valueOf(typeStr);


                    Iteme loadedItem = ItemGenerator.generateRandomItem(type);


                    loadedItem.name = name;
                    loadedItem.bonusStr = bStr;
                    loadedItem.bonusInte = bInte;
                    loadedItem.bonusAgi = bAgi;
                    loadedItem.bonusTho = bTho;


                    if (loc.equals("BAG")) {
                        inv.bag.add(loadedItem);
                    } else if (loc.equals("HEAD")) { inv.head = loadedItem; }
                    else if (loc.equals("CHEST")) { inv.chest = loadedItem; }
                    else if (loc.equals("HANDS")) { inv.hands = loadedItem; }
                    else if (loc.equals("BOOTS")) { inv.boots = loadedItem; }
                    else if (loc.equals("CLOAK")) { inv.cloak = loadedItem; }
                    else if (loc.equals("RING")) { inv.ring = loadedItem; }
                    else if (loc.startsWith("ARTIFACT_")) {
                        int idx = Integer.parseInt(loc.split("_")[1]);
                        if (idx >= 0 && idx < inv.artifacts.length) {
                            inv.artifacts[idx] = loadedItem;
                        }
                    }
                }
            }


            inv.applyTotalStats();
            System.out.println("Încărcare TOTALĂ  reușită!");

        } catch (SQLException e) {
            System.err.println("Eroare la încărcarea salvării!");
            e.printStackTrace();
        }
    }
}
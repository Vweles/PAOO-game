package PaooGame.Database;

import PaooGame.State.PlayState;
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:joc_salvare.db";

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(URL)) {
                if (conn != null) {
                    // AM ADAUGAT COLOANA 'mana'
                    String sql = "CREATE TABLE IF NOT EXISTS savegame (" +
                            "id INTEGER PRIMARY KEY, " +
                            "hp INTEGER, " +
                            "mana INTEGER, " +
                            "x REAL, " +
                            "y REAL, " +
                            "level INTEGER)";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);
                    System.out.println("Baza de date inițializată cu succes (cu suport pentru Mana).");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(int hp, int mana, float x, float y, int level) {
        String sql = "INSERT OR REPLACE INTO savegame(id, hp, mana, x, y, level) VALUES(1, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hp);
            pstmt.setInt(2, mana);
            pstmt.setFloat(3, x);
            pstmt.setFloat(4, y);
            pstmt.setInt(5, level);

            pstmt.executeUpdate();
            System.out.println("Joc Salvat! (HP: " + hp + ", MP: " + mana + ", Lvl: " + level + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void load(PlayState playState) {
        String sql = "SELECT * FROM savegame WHERE id = 1";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int hp = rs.getInt("hp");
                int mana = rs.getInt("mana");
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                int level = rs.getInt("level");

                playState.loadGameData(hp, mana, x, y, level);
                System.out.println("Joc Încărcat! Lvl: " + level + " | Poziție: " + x + ", " + y);
            } else {
                System.out.println("Nu s-a găsit nicio salvare în baza de date.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
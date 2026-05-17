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
                    String sql = "CREATE TABLE IF NOT EXISTS savegame (" +
                            "id INTEGER PRIMARY KEY, " +
                            "hp INTEGER, " +
                            "x REAL, " +
                            "y REAL, " +
                            "level INTEGER)";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);
                    System.out.println("Baza de date inițializată cu succes.");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Eroare: Driverul SQLite nu a fost găsit! Adaugă .jar-ul în Project Structure -> Libraries.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Eroare la inițializarea bazei de date.");
            e.printStackTrace();
        }
    }


    public static void save(int hp, float x, float y, int level) {

        String sql = "INSERT OR REPLACE INTO savegame(id, hp, x, y, level) VALUES(1, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hp);
            pstmt.setFloat(2, x);
            pstmt.setFloat(3, y);
            pstmt.setInt(4, level);

            pstmt.executeUpdate();
            System.out.println("Joc Salvat! (HP: " + hp + ", Lvl: " + level + ")");

        } catch (SQLException e) {
            System.err.println("Eroare la salvarea datelor.");
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
                float x = rs.getFloat("x");
                float y = rs.getFloat("y");
                int level = rs.getInt("level");


                playState.loadGameData(hp, x, y, level);
                System.out.println("Joc Încărcat! Poziție: " + x + ", " + y);
            } else {
                System.out.println("Nu s-a găsit nicio salvare în baza de date.");
            }

        } catch (SQLException e) {
            System.err.println("Eroare la încărcarea datelor.");
            e.printStackTrace();
        }
    }
}
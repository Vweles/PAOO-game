package PaooGame.Database;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:joc_salvare.db";

    public static void init() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS savegame (" +
                    "id INTEGER PRIMARY KEY, " +
                    "hp INTEGER, " +
                    "x REAL, " +
                    "y REAL, " +
                    "level INTEGER)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) { e.printStackTrace(); }
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
            System.out.println("Joc Salvat!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static ResultSet load() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT * FROM savegame WHERE id = 1");
        } catch (SQLException e) { return null; }
    }
}
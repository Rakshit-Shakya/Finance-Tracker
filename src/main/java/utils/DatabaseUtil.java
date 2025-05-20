package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:database/finaitrack.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // ✅ Call this once at app startup
    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "date TEXT NOT NULL, " +
                     "description TEXT NOT NULL, " +
                     "amount REAL NOT NULL, " +
                     "category TEXT)";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Database initialized and table ensured.");
        } catch (SQLException e) {
            System.err.println("❌ Failed to initialize DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

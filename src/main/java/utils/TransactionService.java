package utils;

import models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public static List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseUtil.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions ORDER BY date DESC")) {
            while (rs.next()) {
                list.add(new Transaction(
                    rs.getInt("id"),
                    rs.getString("date"),
                    rs.getString("description"),
                    rs.getDouble("amount"),
                    rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addTransaction(Transaction t) {
    String sql = "INSERT INTO transactions(date, description, amount, category) VALUES(?,?,?,?)";
    try (Connection conn = DatabaseUtil.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, t.getDate());
        stmt.setString(2, t.getDescription());
        stmt.setDouble(3, t.getAmount());
        stmt.setString(4, t.getCategory());
        int rows = stmt.executeUpdate();  // ✅ NEW

        System.out.println("Rows inserted: " + rows);  // ✅ DEBUG
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static void deleteTransaction(int id) {
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM transactions WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

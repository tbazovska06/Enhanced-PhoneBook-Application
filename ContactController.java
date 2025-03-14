import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ContactController {

    public static void loadContacts(DefaultTableModel tableModel) {
        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {

            tableModel.setRowCount(0); 
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
        }
    }

    public static boolean addContact(String name, String phone, String email, String address) {
        if (name.isEmpty() || phone.isEmpty()) return false;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO contacts (name, phone, email, address) VALUES (?, ?, ?, ?)")) {

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding contact: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateContact(int id, String name, String phone, String email, String address) {
        if (name.isEmpty() || phone.isEmpty()) return false;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE contacts SET name=?, phone=?, email=?, address=? WHERE id=?")) {

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, address);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating contact: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteContact(int id) {
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM contacts WHERE id=?")) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }
}

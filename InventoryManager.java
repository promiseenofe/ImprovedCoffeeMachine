import java.sql.*;

public class InventoryManager {
    private Connection connection;
    
    public InventoryManager(Connection connection) {
        this.connection = connection;
        initializeInventory();
    }
    
    private void initializeInventory() {
        try (Statement stmt = connection.createStatement()) {
            // Check if inventory items exist, if not insert them
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM inventory");
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO inventory VALUES ('coffee_beans', 1000)"); // in grams
                stmt.executeUpdate("INSERT INTO inventory VALUES ('milk', 5000)"); // in ml
                stmt.executeUpdate("INSERT INTO inventory VALUES ('syrup', 1000)"); // in ml
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean useIngredients(String coffeeType, String size) {
        try {
            // Start transaction
            connection.setAutoCommit(false);
            
            // Deduct ingredients based on coffee type and size
            double beanMultiplier = size.equals("large") ? 1.5 : (size.equals("small") ? 0.75 : 1.0);
            
            PreparedStatement ps = connection.prepareStatement(
                "UPDATE inventory SET quantity = quantity - ? WHERE item = ?");
            
            // Deduct coffee beans
            ps.setDouble(1, 20 * beanMultiplier); // 20g per standard coffee
            ps.setString(2, "coffee_beans");
            ps.executeUpdate();
            
            // For latte, deduct milk
            if (coffeeType.equalsIgnoreCase("latte")) {
                ps.setDouble(1, 200 * beanMultiplier); // 200ml milk per latte
                ps.setString(2, "milk");
                ps.executeUpdate();
            }
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void restockItem(String item, double amount) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE inventory SET quantity = quantity + ? WHERE item = ?")) {
            ps.setDouble(1, amount);
            ps.setString(2, item);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getInventoryStatus() {
        StringBuilder sb = new StringBuilder();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inventory")) {
            while (rs.next()) {
                sb.append(String.format("%-15s: %.1f %s\n", 
                    rs.getString("item"), 
                    rs.getDouble("quantity"),
                    rs.getString("item").equals("coffee_beans") ? "g" : "ml"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
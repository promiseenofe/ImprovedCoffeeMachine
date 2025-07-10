import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Latte extends Coffee {
    private String milkType;
    private String syrupFlavor;
    private boolean extraFoam;
    
    public Latte(String name, String roast, double price, 
                String milkType, String syrupFlavor, String size, boolean extraFoam) {
        super(name, roast, price, size);
        this.milkType = milkType;
        this.syrupFlavor = syrupFlavor;
        this.extraFoam = extraFoam;
    }
    
    @Override
    public void grindBeans() {
        System.out.println("Grinding coffee beans coarsely for a latte (medium grind)...");
    }
    
    @Override
    public void brewCoffee() {
        super.brewCoffee();
        System.out.println("Steaming " + milkType + " milk...");
        if (!syrupFlavor.equals("none")) {
            System.out.println("Adding " + syrupFlavor + " syrup...");
        }
        if (extraFoam) {
            System.out.println("Adding extra foam...");
        }
        System.out.println("Combining coffee and steamed milk...");
    }
    
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Milk type: " + milkType);
        if (!syrupFlavor.equals("none")) {
            System.out.println("Syrup flavor: " + syrupFlavor);
        }
        System.out.printf("Total price: $%.2f\n", calculatePrice());
    }
    
    @Override
    public double calculatePrice() {
        double basePrice = super.calculatePrice();
        if (milkType.equals("almond") || milkType.equals("oat")) basePrice += 0.75;
        if (!syrupFlavor.equals("none")) basePrice += 0.50;
        if (extraFoam) basePrice += 0.30;
        return basePrice;
    }
    
    public void saveToDatabase(Connection connection) throws SQLException {
        String sql = "INSERT INTO orders(type, roast, size, milk_type, syrup, price) " +
                     "VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "latte");
            pstmt.setString(2, roast);
            pstmt.setString(3, size);
            pstmt.setString(4, milkType);
            pstmt.setString(5, syrupFlavor.equals("none") ? null : syrupFlavor);
            pstmt.setDouble(6, calculatePrice());
            pstmt.executeUpdate();
        }
    }
}
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Espresso extends Coffee {
    private int numberOfShots;
    private boolean withSugar;
    private boolean withCream;
    
    public Espresso(String name, String roast, double price, int numberOfShots, 
                   String size, boolean withSugar, boolean withCream) {
        super(name, roast, price, size);
        this.numberOfShots = numberOfShots;
        this.withSugar = withSugar;
        this.withCream = withCream;
    }
    
    @Override
    public void grindBeans() {
        System.out.println("Grinding " + numberOfShots + " shots of espresso beans finely...");
    }
    
    @Override
    public void brewCoffee() {
        System.out.printf("Brewing %d shots of %s at %d°C under high pressure...\n", 
                         numberOfShots, name, temperature);
        if (withSugar) System.out.println("Adding sugar...");
        if (withCream) System.out.println("Adding cream...");
    }
    
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Number of shots: " + numberOfShots);
        System.out.printf("Total price: $%.2f\n", calculatePrice() * numberOfShots);
    }
    
    @Override
    public double calculatePrice() {
        double basePrice = super.calculatePrice();
        if (withSugar) basePrice += 0.25;
        if (withCream) basePrice += 0.50;
        return basePrice * numberOfShots;
    }
    
    public void saveToDatabase(Connection connection) throws SQLException {
        String sql = "INSERT INTO orders(type, roast, size, price) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "espresso");
            pstmt.setString(2, roast);
            pstmt.setString(3, size);
            pstmt.setDouble(4, calculatePrice());
            pstmt.executeUpdate();
        }
    }
}
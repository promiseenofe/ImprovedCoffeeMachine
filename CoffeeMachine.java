import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

public class CoffeeMachine extends Application {
    private Connection connection;
    private InventoryManager inventory;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database connection
            initializeDatabase();
            inventory = new InventoryManager(connection);
            
            // Create main layout
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(15));
            
            // Create menu selection
            VBox menuBox = new VBox(10);
            Label titleLabel = new Label("Coffee Machine");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            
            Button espressoBtn = new Button("Espresso");
            Button latteBtn = new Button("Latte");
            Button inventoryBtn = new Button("Inventory");
            Button historyBtn = new Button("Order History");
            
            menuBox.getChildren().addAll(titleLabel, espressoBtn, latteBtn, inventoryBtn, historyBtn);
            root.setLeft(menuBox);
            
            // Set button actions
            espressoBtn.setOnAction(e -> showEspressoDialog());
            latteBtn.setOnAction(e -> showLatteDialog());
            inventoryBtn.setOnAction(e -> showInventory());
            historyBtn.setOnAction(e -> showOrderHistory());
            
            // Create and show the scene
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("Advanced Coffee Machine");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to connect to database: " + e.getMessage());
        }
    }
    
    private void initializeDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:coffee.db");
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS orders (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "type TEXT, roast TEXT, size TEXT, " +
                         "milk_type TEXT, syrup TEXT, price REAL, " +
                         "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS inventory (" +
                         "item TEXT PRIMARY KEY, quantity REAL)");
        }
    }
    
    private void showEspressoDialog() {
        // Implementation for espresso order dialog
    }
    
    private void showLatteDialog() {
        // Implementation for latte order dialog
    }
    
    private void showInventory() {
        // Implementation to show inventory
    }
    
    private void showOrderHistory() {
        // Implementation to show order history
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @Override
    public void stop() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
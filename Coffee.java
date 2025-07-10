import java.io.Serializable;

public class Coffee implements Serializable {
    protected String name;
    protected String roast;
    protected int caffeineLevelInMg;
    protected double price;
    protected String size; // New field: small, medium, large
    protected int temperature; // New field: brewing temperature
    
    public Coffee(String name, String roast, double price, String size) {
        this.name = name;
        this.roast = roast;
        this.price = price;
        this.size = size;
        setCaffeineLevel();
        setTemperature();
    }

    private void setTemperature() {
        switch (roast.toLowerCase()) {
            case "light": this.temperature = 90; break;
            case "medium": this.temperature = 95; break;
            case "dark": this.temperature = 100; break;
            default: this.temperature = 92;
        }
    }

    public void setCaffeineLevel() {
        switch (roast.toLowerCase()) {
            case "light": caffeineLevelInMg = 50; break;
            case "medium": caffeineLevelInMg = 100; break;
            case "dark": caffeineLevelInMg = 150; break;
            default: caffeineLevelInMg = 0;
        }
        
        // Adjust for size
        switch (size.toLowerCase()) {
            case "small": caffeineLevelInMg = (int)(caffeineLevelInMg * 0.75); break;
            case "large": caffeineLevelInMg = (int)(caffeineLevelInMg * 1.5); break;
        }
    }

    public void grindBeans() {
        System.out.println("\nGrinding beans for " + name + "...");
    }

    public void brewCoffee() {
        System.out.println("\nBrewing your favorite " + name + " at " + temperature + "°C...");
    }

    public void printInfo() {
        System.out.println("\nYou ordered a " + size + " " + name + " with a " + roast + " roast.");
        System.out.println("The caffeine level in your coffee is " + caffeineLevelInMg + " mg.");
    }
    
    // New method for getting description
    public String getDescription() {
        return size + " " + name + " (" + roast + " roast)";
    }
    
    // New method for calculating final price
    public double calculatePrice() {
        double finalPrice = price;
        switch (size.toLowerCase()) {
            case "small": finalPrice *= 0.9; break;
            case "large": finalPrice *= 1.3; break;
        }
        return finalPrice;
    }
}
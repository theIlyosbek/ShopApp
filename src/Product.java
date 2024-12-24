public class Product {
    private final String name;
    private int stockLevel;
    private double price;

    // extra attributes
    private int sold;
    private double income;

    private static double totalIncome;

    public Product(String name, int stockLevel, double price) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.price = price;
        this.sold = 0;
        this.income = 0.0;
    }

    public double buyStock(int stockLevel) {
        this.stockLevel += stockLevel;
        return (double)(this.stockLevel * price);
    }

    public boolean sell(int stockLevel) {
        if(this.stockLevel >= stockLevel) {
            this.stockLevel -= stockLevel;
            sold += stockLevel;
            income += (price * stockLevel);
            totalIncome += (price * stockLevel);
            return true;
        }
        return false;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public double getPrice() {
        return price;
    }

    public int getSold() {
        return sold;
    }

    public double getIncome() {
        return income;
    }

    public static double getTotalIncome() {
        return totalIncome;
    }
}
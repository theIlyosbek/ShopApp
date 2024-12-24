import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class ShopApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Product[] products = {
                new Product("Bag", 25, 45.0),
                new Product("HP Laptop", 10, 500.0),
                new Product("Desk", 40, 160.0),
                new Product("ASUS Laptop", 30, 700.0),
                new Product("Gaming chair", 40, 200.0)
        };

        System.out.println("Student ID: xxx_xxx\n");
        clearLogFile("Log.txt");
        logToFile("Program started.");
        PrintProducts(products);
        while (true) {
            System.out.println();
            System.out.println("====== ShopApp ======");
            System.out.println("1. Display Products");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Product");
            System.out.println("4. Reprice Product");
            System.out.println("5. Total Stock Value");
            System.out.println("6. Total Income");
            System.out.println("7. Sold Products");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = getIntInput(scanner);

            System.out.println();
            switch (choice) {
                case 1:
                    PrintProducts(products);
                    break;
                case 2:
                    System.out.print("Enter product index (0-4): ");
                    int index = getIntInput(scanner);
                    int qty = 0;
                    if(checkIndex(index, products.length)) {
                        System.out.print("Enter quantity to add: ");
                        qty = getIntInput(scanner);
                        if(qty > 0) {
                            double cost = products[index].buyStock(qty);
                            System.out.println("Bought stock for £" + cost);

                            logToFile("Bought " + qty + " units of " + products[index].getName() + " for £" + cost);
                        } else {
                            System.out.println("Quantity cannot be 0 or negative. Try again.");
                            continue;
                        }
                    } else {
                        System.out.println("Invalid product index. Try again.");
                        continue;
                    }
                    break;
                case 3:
                    System.out.print("Enter product index (0-4): ");
                    index = getIntInput(scanner);
                    if(checkIndex(index, products.length)) {
                        System.out.print("Enter quantity to sell: ");
                        qty = getIntInput(scanner);
                        if(qty > 0) {
                            if (products[index].sell(qty)) {
                                System.out.println("Sale successful!");
                                logToFile("Sold " + qty + " units of " + products[index].getName() + " for £" + (products[index].getPrice() * qty));
                                break;
                            } else {
                                System.out.println("Not enough stock!");
                            }
                        } else {
                            System.out.println("Quantity cannot be 0 or negative. Try again.");
                            continue;
                        }
                    } else {
                        System.out.println("Invalid product index. Try again.");
                        continue;
                    }
                    break;
                case 4:
                    System.out.print("Enter product index (0-4): ");
                    index = getIntInput(scanner);
                    if(checkIndex(index, products.length)) {
                        System.out.print("Enter new price: ");
                        double newPrice = getDoubleInput(scanner);
                        if(newPrice > 0) {
                            products[index].setPrice(newPrice);
                            System.out.println("Price updated.");
                            logToFile(products[index].getName() + " product price has been updated to " + newPrice + ".");
                        } else {
                            System.out.println("Price cannot be 0 or less than 0. Try again.");
                            continue;
                        }
                    } else {
                        System.out.println("Invalid product index. Try again.");
                        continue;
                    }
                    break;
                case 5:
                    double totalValue = 0;
                    for (Product product : products) {
                        totalValue += product.getStockLevel() * product.getPrice();
                    }
                    System.out.println("Total value of all stock: £" + totalValue);
                    logToFile("Total value of all stock: " + totalValue);
                    break;
                case 6:
                    System.out.printf("£%.2f%n", Product.getTotalIncome());
                    logToFile("Total income: £" + Product.getTotalIncome());
                    break;
                case 7:
                    boolean anySold = false;
                    for (Product product : products) {
                        if(product.getSold() > 0) {
                            System.out.println(product.getSold() + " units of " + product.getName() + " sold");
                            logToFile(product.getSold() + " units of " + product.getName() + " sold");
                            anySold = true;
                        }
                    }
                    if(!anySold) {
                        System.out.println("None products are sold yet.");
                        continue;
                    }
                    break;
                case 8:
                    System.out.println("Exiting...");
                    logToFile("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    public static void PrintProducts(Product[] products) {
        int i=0;
        for(Product product : products) {
            System.out.println("(" + i + ") " + product.getName() + ": " +
                    product.getStockLevel() + " in stock, £" + product.getPrice());
            logToFile("(" + i + ") " + product.getName() + ": " +
                    product.getStockLevel() + " in stock, £" + product.getPrice());
            i++;
        }
    }

    public static boolean checkIndex(int index, int arrayLength) {
        return index >= 0 && index < arrayLength;
    }

    public static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static double getDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    public static void logToFile(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Log.txt", true))) {
            // Write timestamp and message
            writer.write(LocalDateTime.now() + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void clearLogFile(String logFileName) {
        try {
            // Check if file exists and clears it before starting the program
            if (Files.exists(Paths.get(logFileName))) {
                // Overwrite the file with an empty content
                new BufferedWriter(new FileWriter(logFileName, false)).close();
            }
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }
}
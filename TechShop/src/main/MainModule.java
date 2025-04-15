package main;

import dao.*;

import entity.*;

import exception.*;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) throws DatabaseConnectionException, OrderNotFoundException, OrderDetailNotFoundException {
        Scanner scan = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        IProductsService productService = new ProductsService();
        OrderService orderService = new OrderService();
        OrderDetailsImplementation orderDetailsService = new OrderDetailsImplementation();
        InventoryService inventoryService = new InventoryService(); 
        while (true) {
            System.out.println("\n--- TECHSHOP MANAGEMENT SYSTEM ---");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
            System.out.println("4. Order Details Management");
            System.out.println("5. Inventory Management");
            System.out.println("6. Exit");
            System.out.print("Choose a module (1-6): ");
            int module = scan.nextInt();
            scan.nextLine(); 

            switch (module) {
                case 1 -> customerMenu(scan, customerService);
                case 2 -> productMenu(scan, productService);
                case 3 -> orderMenu(scan, orderService);
                case 4 -> orderDetailsMenu(scan, orderDetailsService);
                case 5 -> inventoryMenu(scan, inventoryService);
                case 6 -> {
                    System.out.println("Exiting application. Goodbye!");
                    scan.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    

	



	// -------------------- Customer Menu --------------------
    private static void customerMenu(Scanner scan, CustomerService service) {
        System.out.println("\n--- Customer Management ---");
        System.out.println("1. Register Customer");
        System.out.println("2. Get Customer by ID");
        System.out.println("3. Update Customer Info");
        System.out.println("4. Calculate Total Orders");
        System.out.print("Choose an option (1-4): ");
        int key = scan.nextInt();
        scan.nextLine(); // consume newline

        switch (key) {
            case 1 -> registerCustomer(scan, service);
            case 2 -> getCustomerById(scan, service);
            case 3 -> updateCustomerInfo(scan, service);
            case 4 -> calculateTotalOrders(scan, service);
            default -> System.out.println("Invalid option. Returning to main menu.");
        }
    }

    private static void registerCustomer(Scanner scan, CustomerService service) {
        System.out.println("Enter customer details:");
        System.out.print("First Name: ");
        String firstName = scan.nextLine();
        System.out.print("Last Name: ");
        String lastName = scan.nextLine();
        System.out.print("Email: ");
        String email = scan.nextLine();
        System.out.print("Phone: ");
        String phone = scan.nextLine();
        System.out.print("Address: ");
        String address = scan.nextLine();

        Customer customer = new Customer(0, firstName, lastName, email, phone, address);
        try {
            boolean result = service.insertCustomer(customer);
            System.out.println(result ? "Customer registered successfully." : "Failed to register customer.");
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getCustomerById(Scanner scan, CustomerService service) {
        System.out.print("Enter Customer ID: ");
        int id = scan.nextInt();
        try {
            Customer customer = service.getCustomerById(id);
            System.out.println("Customer Details: " + customer);
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateCustomerInfo(Scanner scan, CustomerService service) {
        System.out.print("Enter Customer ID to update: ");
        int id = scan.nextInt();
        scan.nextLine(); // consume newline

        System.out.println("Select field to update:");
        System.out.println("1. Email");
        System.out.println("2. Phone");
        System.out.println("3. Address");
        System.out.print("Option: ");
        int field = scan.nextInt();
        scan.nextLine(); // consume newline

        System.out.print("Enter new value: ");
        String newValue = scan.nextLine();

        try {
            boolean result = service.updateCustomerInfo(id, field, newValue);
            System.out.println(result ? "Customer info updated." : "Update failed.");
        } catch (CustomerNotFoundException | InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void calculateTotalOrders(Scanner scan, CustomerService service) {
        System.out.print("Enter Customer ID: ");
        int id = scan.nextInt();
        try {
            int totalOrders = service.calculateTotalOrders(id);
            System.out.println("Total Orders: " + totalOrders);
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // -------------------- Product Menu --------------------
    private static void productMenu(Scanner scan, IProductsService service) {
        System.out.println("\n--- Product Management ---");
        System.out.println("1. Get Product Details");
        System.out.println("2. Update Product Info");
        System.out.println("3. Check Product Stock");
        System.out.print("Choose an option (1-3): ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Product ID: ");
                int id = scan.nextInt();
                try {
                    Product product = service.getProductDetails(id);
                    System.out.println("Product Details: " + product);
                } catch (ProductNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                System.out.print("Enter Product ID to update: ");
                int id = scan.nextInt();
                System.out.println("Select field to update:");
                System.out.println("1. Price");
                System.out.println("2. Description");
                System.out.print("Option: ");
                int field = scan.nextInt();
                scan.nextLine();
                System.out.print("Enter new value: ");
                String newValue = scan.nextLine();
                try {
                    boolean updated = service.updateProductInfo(id, field, newValue);
                    System.out.println(updated ? "Product updated successfully." : "Update failed.");
                } catch (ProductNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format.");
                }
            }
            case 3 -> {
                System.out.print("Enter Product ID: ");
                int id = scan.nextInt();
                try {
                    boolean inStock = service.isProductInStock(id);
                    System.out.println(inStock ? "In stock." : "Out of stock.");
                } catch (ProductNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            default -> System.out.println("Invalid option. Returning to main menu.");
        }
    }

    // -------------------- Order Menu --------------------
    private static void orderMenu(Scanner scan, OrderService service) throws OrderNotFoundException {
        System.out.println("\n--- Order Management ---");
        System.out.println("1. Calculate Total Amount");
        System.out.println("2. Get Order Details");
        System.out.println("3. Update Order Status");
        System.out.println("4. Cancel Order");
        System.out.print("Choose an option (1-4): ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Order ID: ");
                int id = scan.nextInt();
                double total = service.calculateTotalAmount(id);
                System.out.println("Total Amount: ₹" + total);
            }
            case 2 -> {
                System.out.print("Enter Order ID: ");
                int id = scan.nextInt();
                service.getOrderDetails(id);
            }
            case 3 -> {
                System.out.print("Enter Order ID: ");
                int id = scan.nextInt();
                scan.nextLine(); // consume newline
                System.out.print("Enter new status: ");
                String status = scan.nextLine();
                boolean updated = service.updateOrderStatus(id, status);
                System.out.println(updated ? "Order updated!" : "Update failed.");
            }
            case 4 -> {
                System.out.print("Enter Order ID to cancel: ");
                int id = scan.nextInt();
                boolean cancelled = service.cancelOrder(id);
                System.out.println(cancelled ? "Order cancelled!" : "Cancellation failed.");
            }
            default -> System.out.println("Invalid option. Returning to main menu.");
        }
    }
       // -----------------------------------------------------order details menu--------------------
        private static void orderDetailsMenu(Scanner scan, OrderDetailsImplementation service) throws OrderDetailNotFoundException {
            System.out.println("\n--- Order Details Management ---");
            System.out.println("1. Calculate Subtotal");
            System.out.println("2. Get Order Detail Info");
            System.out.println("3. Update Quantity");
            System.out.println("4. Add Discount");
            System.out.print("Choose an option (1-4): ");
            int choice = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Order Detail ID: ");
                    int id = scan.nextInt();
                    OrderDetails detail = service.fetchOrderDetailById(id);
                    double subtotal = service.calculateSubtotal(detail);
                    System.out.println("Subtotal: ₹" + subtotal);
                }
                case 2 -> {
                    System.out.print("Enter Order Detail ID: ");
                    int id = scan.nextInt();
                    OrderDetails detail = service.fetchOrderDetailById(id);
                    service.getOrderDetailInfo(detail);
                }
                case 3 -> {
                    System.out.print("Enter Order Detail ID: ");
                    int id = scan.nextInt();
                    OrderDetails detail = service.fetchOrderDetailById(id);
                    System.out.print("Enter new quantity: ");
                    int quantity = scan.nextInt();
                    service.updateQuantity(detail, quantity);
                }
                case 4 -> {
                    System.out.print("Enter Order Detail ID: ");
                    int id = scan.nextInt();
                    OrderDetails detail = service.fetchOrderDetailById(id);
                    System.out.print("Enter discount percentage: ");
                    double discount = scan.nextDouble();
                    service.addDiscount(detail, discount);
                }
                default -> System.out.println("Invalid option. Returning to main menu.");
            }
        
            
    }
     // -------------------- Inventory Menu --------------------
        private static void inventoryMenu(Scanner scan, InventoryService inventoryService) {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. Add to Inventory");
            System.out.println("2. Remove from Inventory");
            System.out.println("3. Update Stock Quantity");
            System.out.println("4. Check Product Availability");
            System.out.println("5. Get Inventory Value");
            System.out.println("6. List Low Stock Products");
            System.out.println("7. List Out of Stock Products");
            System.out.println("8. List All Products in Inventory");
            System.out.print("Choose an option (1-8): ");
            int key = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (key) {
                case 1 -> {
                    System.out.print("Enter Inventory ID: ");
                    int id = scan.nextInt();
                    System.out.print("Enter quantity to add: ");
                    int qty = scan.nextInt();
                    inventoryService.addToInventory(id, qty);
                }
                case 2 -> {
                    System.out.print("Enter Inventory ID: ");
                    int id = scan.nextInt();
                    System.out.print("Enter quantity to remove: ");
                    int qty = scan.nextInt();
                    inventoryService.removeFromInventory(id, qty);
                }
                case 3 -> {
                    System.out.print("Enter Inventory ID: ");
                    int id = scan.nextInt();
                    System.out.print("Enter new stock quantity: ");
                    int qty = scan.nextInt();
                    inventoryService.updateStockQuantity(id, qty);
                }
                case 4 -> {
                    System.out.print("Enter Inventory ID: ");
                    int id = scan.nextInt();
                    System.out.print("Enter quantity to check: ");
                    int qty = scan.nextInt();
                    boolean available = inventoryService.isProductAvailable(id, qty);
                    System.out.println(available ? "Product is available." : "Product is NOT available.");
                }
                case 5 -> {
                    System.out.print("Enter Inventory ID: ");
                    int id = scan.nextInt();
                    double value = inventoryService.getInventoryValue(id);
                    System.out.println("Total Inventory Value: ₹" + value);
                }
                case 6 -> {
                    System.out.print("Enter threshold quantity: ");
                    int threshold = scan.nextInt();
                    List<Inventory> lowStock = inventoryService.listLowStockProducts(threshold);
                    if (lowStock.isEmpty()) {
                        System.out.println("No low stock products.");
                    } else {
                        lowStock.forEach(System.out::println);
                    }
                }
                case 7 -> {
                    List<Inventory> outOfStock = inventoryService.listOutOfStockProducts();
                    if (outOfStock.isEmpty()) {
                        System.out.println("No out of stock products.");
                    } else {
                        outOfStock.forEach(System.out::println);
                    }
                }
                case 8 -> inventoryService.listAllProducts();
                default -> System.out.println("Invalid option.");
            } 
}}

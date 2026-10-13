package retailiq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import retailiq.algorithms.stringalgo.Trie;
import retailiq.model.Product;
import retailiq.service.RetailIQService;
import retailiq.storage.ProductStorage;

public class RetailIQApp {

        // =====================================================
        // GLOBAL DATA
        // =====================================================

        private static final Scanner scanner = new Scanner(System.in);

        private static final List<Product> products = new ArrayList<>();

        private static RetailIQService service;

        // =====================================================
        // MAIN
        // =====================================================

        public static void main(String[] args) {

                System.out.println(
                                "=================================================");

                System.out.println(
                                "       RETAILIQ - INTELLIGENT RETAIL");

                System.out.println(
                                "             ANALYTICS PLATFORM");

                System.out.println(
                                "=================================================");

                // Load products from storage
                loadProductsFromFile();

                // Create service using current products
                service = new RetailIQService(products);

                boolean running = true;

                while (running) {

                        displayMenu();

                        int choice = readInt(
                                        "Enter your choice: ");

                        switch (choice) {

                                case 1:
                                        addProduct();
                                        break;

                                case 2:
                                        viewProducts();
                                        break;

                                case 3:
                                        searchProducts();
                                        break;

                                case 4:
                                        autocomplete();
                                        break;

                                case 5:
                                        correctQuery();
                                        break;

                                case 6:
                                        budgetSelection();
                                        break;

                                case 7:
                                        suffixAnalysis();
                                        break;

                                case 8:
                                        substringSearch();
                                        break;

                                case 9:
                                        matrixChainAnalysis();
                                        break;

                                case 10:

                                        System.out.println(
                                                        "\nThank you for using RetailIQ!");

                                        running = false;

                                        break;

                                default:

                                        System.out.println(
                                                        "\nInvalid choice.");
                        }
                }

                scanner.close();
        }

        // =====================================================
        // MENU
        // =====================================================

        private static void displayMenu() {

                System.out.println(
                                "\n================ RETAILIQ MENU ================");

                System.out.println(
                                "1. Add Product");

                System.out.println(
                                "2. View Products");

                System.out.println(
                                "3. Search Products");

                System.out.println(
                                "4. Product Autocomplete");

                System.out.println(
                                "5. Correct Customer Query");

                System.out.println(
                                "6. Budget-Based Product Selection");

                System.out.println(
                                "7. Suffix Array + Kasai Analysis");

                System.out.println(
                                "8. Suffix Automaton Search");

                System.out.println(
                                "9. Matrix Chain DP");

                System.out.println(
                                "10. Exit");

                System.out.println(
                                "================================================");
        }

        // =====================================================
        // ADD PRODUCT
        // =====================================================

        private static void addProduct() {

                System.out.println(
                                "\n===== ADD PRODUCT =====");

                int id = getNextProductId();

                System.out.print(
                                "Enter product name: ");

                String name = scanner.nextLine().trim();

                if (name.isEmpty()) {

                        System.out.println(
                                        "Product name cannot be empty.");

                        return;
                }

                System.out.print(
                                "Enter category: ");

                String category = scanner.nextLine().trim();

                if (category.isEmpty()) {

                        System.out.println(
                                        "Category cannot be empty.");

                        return;
                }

                double price = readPositiveDouble(
                                "Enter price: ₹");

                System.out.print(
                                "Enter product description: ");

                String description = scanner.nextLine().trim();

                if (description.isEmpty()) {

                        System.out.println(
                                        "Description cannot be empty.");

                        return;
                }

                Product product = new Product(
                                id,
                                name,
                                category,
                                price,
                                description);

                // Add to memory
                service.addProduct(product);

                // Save permanently
                ProductStorage.saveProducts(
                                products);

                System.out.println(
                                "\nProduct added successfully!");

                System.out.println(
                                "Product ID: "
                                                + id);

                System.out.println(
                                "Product saved permanently.");
        }

        // =====================================================
        // VIEW PRODUCTS
        // =====================================================

        private static void viewProducts() {

                System.out.println(
                                "\n===== PRODUCT DATABASE =====");

                if (products.isEmpty()) {

                        System.out.println(
                                        "No products available.");

                        return;
                }

                for (Product product : products) {

                        System.out.println(
                                        "\n" + product);

                        System.out.println(
                                        "Description: "
                                                        + product.getDescription());

                        System.out.println(
                                        "----------------------------------------");
                }

                System.out.println(
                                "\nTotal Products: "
                                                + products.size());
        }

        // =====================================================
        // PRODUCT SEARCH
        // =====================================================

        private static void searchProducts() {

                if (products.isEmpty()) {

                        System.out.println(
                                        "No products available.");

                        return;
                }

                System.out.println(
                                "\n===== PRODUCT SEARCH =====");

                System.out.print(
                                "Enter search query: ");

                String query = scanner.nextLine()
                                .trim()
                                .toLowerCase();

                if (query.isEmpty()) {

                        System.out.println(
                                        "Search query cannot be empty.");

                        return;
                }

                System.out.println(
                                "\nChoose String Matching Algorithm:");

                System.out.println(
                                "1. Naive Pattern Matching");

                System.out.println(
                                "2. KMP");

                System.out.println(
                                "3. Z-Function");

                System.out.println(
                                "4. Rabin-Karp");

                int algorithm = readInt(
                                "Enter algorithm: ");

                if (algorithm < 1 ||
                                algorithm > 4) {

                        System.out.println(
                                        "Invalid algorithm.");

                        return;
                }

                service.productSearch(
                                query,
                                algorithm);
        }

        // =====================================================
        // TRIE AUTOCOMPLETE
        // =====================================================

        private static void autocomplete() {

                if (products.isEmpty()) {

                        System.out.println(
                                        "No products available.");

                        return;
                }

                System.out.println(
                                "\n===== PRODUCT AUTOCOMPLETE =====");

                System.out.print(
                                "Enter product prefix: ");

                String prefix = scanner.nextLine()
                                .trim()
                                .toLowerCase();

                if (prefix.isEmpty()) {

                        System.out.println(
                                        "Prefix cannot be empty.");

                        return;
                }

                service.productAutocomplete(
                                prefix);
        }

        // =====================================================

        // =====================================================
        // EDIT DISTANCE / QUERY CORRECTION
        // =====================================================

        private static void correctQuery() {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return;
                }

                System.out.println("\n===== CUSTOMER QUERY CORRECTION (EDIT DISTANCE) =====");
                System.out.println(
                                "Tip: Try typing misspelled queries like 'iphne', 'macbok', 'samsng', 'headfones', 'playstaton'");
                System.out.print("Enter customer query: ");

                String query = scanner.nextLine().trim().toLowerCase();

                if (query.isEmpty()) {
                        System.out.println("Query cannot be empty.");
                        return;
                }

                service.queryCorrection(query);
        }

        // =====================================================
        // BITMASK DP
        // =====================================================

        private static void budgetSelection() {
                if (products.isEmpty()) {
                        System.out.println("No products available.");
                        return;
                }

                System.out.println("\n===== BUDGET PRODUCT SELECTION (BITMASK DP) =====");
                double budget = readPositiveDouble("Enter your budget: ₹");

                service.productSelection(budget);
        }

        // =====================================================
        // SUFFIX ARRAY + KASAI
        // =====================================================

        private static void suffixAnalysis() {
                System.out.println("\n===== SUFFIX ARRAY + KASAI ANALYSIS =====");
                System.out.println("1. Select a Product Description from Catalog");
                System.out.println("2. Enter Custom Text");
                int option = readInt("Select option (1-2): ");

                String text;
                if (option == 1 && !products.isEmpty()) {
                        System.out.println("\nSelect Product:");
                        for (int i = 0; i < products.size(); i++) {
                                System.out.println((i + 1) + ". " + products.get(i).getName());
                        }
                        int prodIndex = readInt("Product (1-" + products.size() + "): ") - 1;
                        if (prodIndex < 0 || prodIndex >= products.size()) {
                                prodIndex = 0;
                        }
                        text = products.get(prodIndex).getDescription();
                } else {
                        System.out.print("Enter text for suffix analysis: ");
                        text = scanner.nextLine();
                        if (text.trim().isEmpty()) {
                                System.out.println("Text cannot be empty.");
                                return;
                        }
                }

                service.productDescriptionIndexing(text);
        }

        // =====================================================
        // SUFFIX AUTOMATON
        // =====================================================

        private static void substringSearch() {
                System.out.println("\n===== SUFFIX AUTOMATON SUBSTRING SEARCH =====");
                System.out.println("1. Select a Product Description from Catalog");
                System.out.println("2. Enter Custom Text");
                int option = readInt("Select option (1-2): ");

                String text;
                if (option == 1 && !products.isEmpty()) {
                        System.out.println("\nSelect Product:");
                        for (int i = 0; i < products.size(); i++) {
                                System.out.println((i + 1) + ". " + products.get(i).getName());
                        }
                        int prodIndex = readInt("Product (1-" + products.size() + "): ") - 1;
                        if (prodIndex < 0 || prodIndex >= products.size()) {
                                prodIndex = 0;
                        }
                        text = products.get(prodIndex).getDescription();
                        System.out.println("\nSelected Text: \"" + text + "\"");
                } else {
                        System.out.print("Enter text: ");
                        text = scanner.nextLine();
                        if (text.trim().isEmpty()) {
                                System.out.println("Text cannot be empty.");
                                return;
                        }
                }

                System.out.print("Enter substring to search: ");
                String pattern = scanner.nextLine();

                if (pattern.trim().isEmpty()) {
                        System.out.println("Pattern cannot be empty.");
                        return;
                }

                service.substringSearch(text, pattern);
        }

        // =====================================================
        // MATRIX CHAIN DP
        // =====================================================

        private static void matrixChainAnalysis() {
                System.out.println("\n===== MATRIX CHAIN DP (PIPELINE OPTIMIZATION) =====");
                System.out.println("1. Preset 1: 4-Stage Supply Chain Pipeline [10, 30, 5, 60, 20]");
                System.out.println("2. Preset 2: Retail Tensor Contraction Pipeline [20, 100, 50, 10, 500]");
                System.out.println("3. Custom Matrix Dimensions");
                int option = readInt("Select option (1-3): ");

                int[] dimensions;
                if (option == 1) {
                        dimensions = new int[] { 10, 30, 5, 60, 20 };
                } else if (option == 2) {
                        dimensions = new int[] { 20, 100, 50, 10, 500 };
                } else {
                        int number = readInt("Enter number of matrices: ");
                        if (number <= 0) {
                                System.out.println("Number must be greater than 0.");
                                return;
                        }

                        dimensions = new int[number + 1];
                        System.out.println("\nEnter matrix dimensions (For N matrices enter N+1 dimensions):");
                        for (int i = 0; i <= number; i++) {
                                dimensions[i] = readPositiveInt("Dimension " + (i + 1) + ": ");
                        }
                }

                service.optimizeProcessing(dimensions);
        }

        // =====================================================
        // LOAD PRODUCTS
        // =====================================================

        private static void loadProductsFromFile() {

                products.clear();

                List<Product> savedProducts = ProductStorage.loadProducts();

                if (savedProducts.isEmpty()) {

                        System.out.println(
                                        "\nNo saved products found.");

                        System.out.println(
                                        "Starting with an empty product database.");

                        System.out.println(
                                        "Use option 1 to add your first product.");

                } else {

                        products.addAll(
                                        savedProducts);

                        System.out.println(
                                        "\nSaved products loaded successfully.");

                        System.out.println(
                                        "Products available: "
                                                        + products.size());
                }
        }

        // =====================================================
        // NEXT PRODUCT ID
        // =====================================================

        private static int getNextProductId() {

                int maxId = 0;

                for (Product product : products) {

                        if (product.getId() > maxId) {

                                maxId = product.getId();
                        }
                }

                return maxId + 1;
        }

        // =====================================================
        // INTEGER INPUT
        // =====================================================

        private static int readInt(
                        String message) {

                while (true) {

                        try {

                                System.out.print(
                                                message);

                                return Integer.parseInt(
                                                scanner.nextLine()
                                                                .trim());

                        } catch (NumberFormatException e) {

                                System.out.println(
                                                "Please enter a valid integer.");
                        }
                }
        }

        // =====================================================
        // POSITIVE INTEGER
        // =====================================================

        private static int readPositiveInt(
                        String message) {

                while (true) {

                        int value = readInt(message);

                        if (value > 0) {

                                return value;
                        }

                        System.out.println(
                                        "Enter a value greater than 0.");
                }
        }

        // =====================================================
        // DOUBLE INPUT
        // =====================================================

        private static double readDouble(
                        String message) {

                while (true) {

                        try {

                                System.out.print(
                                                message);

                                return Double.parseDouble(
                                                scanner.nextLine()
                                                                .trim());

                        } catch (NumberFormatException e) {

                                System.out.println(
                                                "Please enter a valid number.");
                        }
                }
        }

        // =====================================================
        // POSITIVE DOUBLE
        // =====================================================

        private static double readPositiveDouble(
                        String message) {

                while (true) {

                        double value = readDouble(message);

                        if (value > 0) {

                                return value;
                        }

                        System.out.println(
                                        "Value must be greater than 0.");
                }
        }
}
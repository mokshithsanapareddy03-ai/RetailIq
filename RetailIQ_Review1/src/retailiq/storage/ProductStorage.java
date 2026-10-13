package retailiq.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retailiq.model.Product;

public class ProductStorage {

    private static final String FILE_NAME = "products.txt";

    // =====================================================
    // SAVE ALL PRODUCTS
    // =====================================================

    public static void saveProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : products) {
                /*
                 * Format:
                 * id|name|category|price|description
                 */
                writer.write(
                        product.getId()
                        + "|"
                        + clean(product.getName())
                        + "|"
                        + clean(product.getCategory())
                        + "|"
                        + product.getPrice()
                        + "|"
                        + clean(product.getDescription())
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // =====================================================
    // LOAD PRODUCTS
    // =====================================================

    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split("\\|", -1);
                    if (parts.length != 5) {
                        continue;
                    }
                    try {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        String category = parts[2];
                        double price = Double.parseDouble(parts[3]);
                        String description = parts[4];

                        products.add(new Product(id, name, category, price, description));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid product line.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading products: " + e.getMessage());
            }
        }

        if (products.isEmpty()) {
            products = getSeedProducts();
            saveProducts(products);
        }

        return products;
    }

    private static List<Product> getSeedProducts() {
        List<Product> seeds = new ArrayList<>();
        seeds.add(new Product(1, "Apple iPhone 15 Pro", "Electronics", 129900.0, "Titanium design, A17 Pro chip, 48MP main camera system, and USB-C port."));
        seeds.add(new Product(2, "Samsung Galaxy S24 Ultra", "Electronics", 129999.0, "Flagship smartphone with Snapdragon 8 Gen 3, S-Pen stylus, 200MP Quad Tele camera, and Galaxy AI features."));
        seeds.add(new Product(3, "Apple MacBook Air M3", "Laptops", 114900.0, "15-inch Liquid Retina display laptop with Apple M3 chip, 8-core CPU, 10-core GPU, 8GB Unified Memory, and 256GB SSD."));
        seeds.add(new Product(4, "Sony WH-1000XM5 Headphones", "Audio", 29990.0, "Industry-leading noise canceling wireless headphones with Auto NC Optimizer and crystal clear hands-free calling."));
        seeds.add(new Product(5, "Dell XPS 13 Laptop", "Laptops", 135000.0, "Premium ultraportable laptop with Intel Core Ultra 7 processor, 13.4-inch FHD+ display, 16GB RAM, and 512GB SSD."));
        seeds.add(new Product(6, "Apple Watch Series 9", "Wearables", 41900.0, "Smartwatch with S9 SiP, Double Tap gesture control, 2000 nits brighter display, and advanced health sensors."));
        seeds.add(new Product(7, "Nike Air Max 270", "Footwear", 12995.0, "Men's sports running shoes featuring large Max Air heel unit for responsive cushioning and breathable mesh upper."));
        seeds.add(new Product(8, "Bose QuietComfort Earbuds II", "Audio", 24900.0, "Wireless noise canceling earbuds with CustomTune technology for personalized sound and noise cancellation."));
        seeds.add(new Product(9, "LG 55-inch 4K OLED Smart TV", "Appliances", 119990.0, "Self-lit OLED pixels, alpha9 AI Processor 4K Gen6, Dolby Vision IQ, Dolby Atmos, and webOS Smart TV."));
        seeds.add(new Product(10, "Dyson V15 Detect Vacuum", "Home Appliances", 62900.0, "Cordless vacuum cleaner with laser illumination to reveal invisible dust, piezo sensor, and anti-tangle Hair Screw tool."));
        seeds.add(new Product(11, "ASUS ROG Strix G16 Gaming Laptop", "Laptops", 159990.0, "High performance gaming laptop with Intel Core i9-13980HX, NVIDIA GeForce RTX 4070 GPU, and 16-inch ROG Nebula Display."));
        seeds.add(new Product(12, "Kindle Paperwhite 16GB", "Electronics", 14999.0, "6.8-inch glare-free display, adjustable warm light, up to 10 weeks battery life, and IPX8 waterproof e-reader."));
        seeds.add(new Product(13, "Instant Pot Duo 7-in-1 Cooker", "Kitchen Appliances", 9990.0, "Electric pressure cooker, slow cooker, rice cooker, steamer, saute pan, yogurt maker, and food warmer."));
        seeds.add(new Product(14, "OnePlus 12 5G", "Electronics", 64999.0, "Snapdragon 8 Gen 3 flagship smartphone with 50MP Hasselblad camera system and 100W SUPERVOOC fast charging."));
        seeds.add(new Product(15, "Sony PlayStation 5 Console", "Gaming", 54990.0, "Next-generation gaming console with ultra-high speed SSD, ray tracing graphics, 3D audio, and DualSense haptic feedback."));
        seeds.add(new Product(16, "Adidas Ultraboost Light Shoes", "Footwear", 18999.0, "Lightweight running shoes featuring Light BOOST cushioning, Continental Rubber outsole, and Primeknit textile upper."));
        seeds.add(new Product(17, "Samsung 49-inch Odyssey OLED G9", "Monitors", 154990.0, "Curved gaming monitor with Dual QHD resolution, 240Hz refresh rate, 0.03ms response time, and Neo Quantum Processor Pro."));
        seeds.add(new Product(18, "Logitech MX Master 3S Mouse", "Accessories", 10995.0, "Performance wireless mouse with 8K DPI sensor, Quiet Clicks, and MagSpeed electromagnetic scrolling."));
        seeds.add(new Product(19, "Philips Air Fryer HD9252", "Kitchen Appliances", 7995.0, "Rapid Air technology air fryer for healthy frying with up to 90% less fat and touch panel presets."));
        seeds.add(new Product(20, "Marshall Stanmore III Bluetooth Speaker", "Audio", 31999.0, "Home wireless speaker with iconic vintage design, room-filling soundstage, and Bluetooth 5.2 connectivity."));
        return seeds;
    }

    // =====================================================
    // REMOVE | FROM USER INPUT
    // =====================================================

    private static String clean(String text) {
        return text
                .replace("|", "/")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}
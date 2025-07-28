package com.clotheswarehouse.config;

import com.clotheswarehouse.model.*;
import com.clotheswarehouse.repository.DistributionCentreItemRepository;
import com.clotheswarehouse.repository.DistributionCentreRepository;
import com.clotheswarehouse.repository.ItemRepository;
import com.clotheswarehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Loading initial data...");
        
        // Load warehouse items
        if (itemRepository.count() == 0) {
            System.out.println("📦 Loading warehouse items...");
            itemRepository.save(new Item("Designer Jacket", Brand.BALENCIAGA, 2022, 1500.0, 5));
            itemRepository.save(new Item("Luxury Sweater", Brand.STONE_ISLAND, 2023, 1200.0, 3));
            itemRepository.save(new Item("Premium Bag", Brand.DIOR, 2022, 2500.0, 2));
            itemRepository.save(new Item("Designer Shoes", Brand.GUCCI, 2023, 1800.0, 8));
            itemRepository.save(new Item("Luxury Watch", Brand.PRADA, 2022, 3000.0, 1));
            itemRepository.save(new Item("Designer Dress", Brand.VERSACE, 2023, 2200.0, 4));
            itemRepository.save(new Item("Premium Suit", Brand.ARMANI, 2022, 2800.0, 2));
            itemRepository.save(new Item("Luxury Perfume", Brand.CHANEL, 2023, 1100.0, 10));
            System.out.println("✅ Warehouse items loaded: " + itemRepository.count());
        }
        
        // Load users
        if (userRepository.count() == 0) {
            System.out.println("👥 Loading users...");
            User admin = new User("admin", passwordEncoder.encode("admin123"), "Administrator", Role.ADMIN);
            User employee = new User("employee", passwordEncoder.encode("emp123"), "Warehouse Employee", Role.WAREHOUSE_EMPLOYEE);
            User user = new User("user", passwordEncoder.encode("user123"), "Regular User", Role.REGULAR_USER);
            
            userRepository.save(admin);
            userRepository.save(employee);
            userRepository.save(user);
            System.out.println("✅ Users loaded: " + userRepository.count());
        }
        
        // Load distribution centres and their items
        if (distributionCentreRepository.count() == 0) {
            System.out.println("🏢 Loading distribution centres...");
            
            // Create 4 distribution centres in GTA area with strategic locations
            // Warehouse is at CN Tower area: 43.6426, -79.3871
            
            // North York - 15.2 km from warehouse
            DistributionCentre northYork = new DistributionCentre("North York Distribution Centre", 43.7615, -79.4111);
            distributionCentreRepository.save(northYork);
            
            // Scarborough - 28.5 km from warehouse  
            DistributionCentre scarborough = new DistributionCentre("Scarborough Distribution Centre", 43.7731, -79.2578);
            distributionCentreRepository.save(scarborough);
            
            // Mississauga - 22.1 km from warehouse
            DistributionCentre mississauga = new DistributionCentre("Mississauga Distribution Centre", 43.5890, -79.6441);
            distributionCentreRepository.save(mississauga);
            
            // Markham - 31.8 km from warehouse (furthest)
            DistributionCentre markham = new DistributionCentre("Markham Distribution Centre", 43.8561, -79.3370);
            distributionCentreRepository.save(markham);
            
            System.out.println("✅ Distribution centres loaded: " + distributionCentreRepository.count());
            
            // Add items to North York centre (closest for most items)
            System.out.println("📦 Loading distribution centre items...");
            
            DistributionCentreItem item1 = new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 1500.0, 15);
            item1.setDistributionCentre(northYork);
            distributionCentreItemRepository.save(item1);
            
            DistributionCentreItem item2 = new DistributionCentreItem("Luxury Sweater", Brand.STONE_ISLAND, 2023, 1200.0, 20);
            item2.setDistributionCentre(northYork);
            distributionCentreItemRepository.save(item2);
            
            DistributionCentreItem item3 = new DistributionCentreItem("Premium Bag", Brand.DIOR, 2022, 2500.0, 8);
            item3.setDistributionCentre(northYork);
            distributionCentreItemRepository.save(item3);
            
            // Add items to Scarborough centre
            DistributionCentreItem item4 = new DistributionCentreItem("Designer Shoes", Brand.GUCCI, 2023, 1800.0, 25);
            item4.setDistributionCentre(scarborough);
            distributionCentreItemRepository.save(item4);
            
            DistributionCentreItem item5 = new DistributionCentreItem("Luxury Watch", Brand.PRADA, 2022, 3000.0, 12);
            item5.setDistributionCentre(scarborough);
            distributionCentreItemRepository.save(item5);
            
            DistributionCentreItem item6 = new DistributionCentreItem("Designer Dress", Brand.VERSACE, 2023, 2200.0, 18);
            item6.setDistributionCentre(scarborough);
            distributionCentreItemRepository.save(item6);
            
            // Add items to Mississauga centre
            DistributionCentreItem item7 = new DistributionCentreItem("Premium Suit", Brand.ARMANI, 2022, 2800.0, 10);
            item7.setDistributionCentre(mississauga);
            distributionCentreItemRepository.save(item7);
            
            DistributionCentreItem item8 = new DistributionCentreItem("Luxury Perfume", Brand.CHANEL, 2023, 1100.0, 30);
            item8.setDistributionCentre(mississauga);
            distributionCentreItemRepository.save(item8);
            
            DistributionCentreItem item9 = new DistributionCentreItem("Designer Handbag", Brand.BALENCIAGA, 2023, 1900.0, 12);
            item9.setDistributionCentre(mississauga);
            distributionCentreItemRepository.save(item9);
            
            // Add items to Markham centre (furthest - to test distance logic)
            DistributionCentreItem item10 = new DistributionCentreItem("Luxury Sneakers", Brand.GUCCI, 2023, 1600.0, 22);
            item10.setDistributionCentre(markham);
            distributionCentreItemRepository.save(item10);
            
            DistributionCentreItem item11 = new DistributionCentreItem("Designer Scarf", Brand.DIOR, 2022, 1300.0, 16);
            item11.setDistributionCentre(markham);
            distributionCentreItemRepository.save(item11);
            
            DistributionCentreItem item12 = new DistributionCentreItem("Premium Sunglasses", Brand.PRADA, 2023, 1400.0, 14);
            item12.setDistributionCentre(markham);
            distributionCentreItemRepository.save(item12);
            
            // Add same item to multiple centres to test distance logic
            DistributionCentreItem duplicateItem1 = new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 1500.0, 5);
            duplicateItem1.setDistributionCentre(mississauga);
            distributionCentreItemRepository.save(duplicateItem1);
            
            DistributionCentreItem duplicateItem2 = new DistributionCentreItem("Designer Jacket", Brand.BALENCIAGA, 2023, 1500.0, 3);
            duplicateItem2.setDistributionCentre(markham);
            distributionCentreItemRepository.save(duplicateItem2);
            
            System.out.println("✅ Distribution centre items loaded: " + distributionCentreItemRepository.count());
        }
        
        System.out.println("🎉 Data loading completed successfully!");
        System.out.println("📍 Warehouse location: CN Tower area (43.6426, -79.3871)");
        System.out.println("🏢 Distribution centres:");
        System.out.println("   • North York: 15.2 km (closest)");
        System.out.println("   • Mississauga: 22.1 km");
        System.out.println("   • Scarborough: 28.5 km");
        System.out.println("   • Markham: 31.8 km (furthest)");
    }
}

package com.distributioncentre.config;

import com.distributioncentre.model.Brand;
import com.distributioncentre.model.DistributionCentre;
import com.distributioncentre.model.Item;
import com.distributioncentre.repository.DistributionCentreRepository;
import com.distributioncentre.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Override
    public void run(String... args) throws Exception {
        if (distributionCentreRepository.count() == 0) {
            // Create distribution centres in GTA area
            DistributionCentre northYork = new DistributionCentre("North York Distribution Centre", 43.7615, -79.4111);
            DistributionCentre scarborough = new DistributionCentre("Scarborough Distribution Centre", 43.7731, -79.2578);
            DistributionCentre mississauga = new DistributionCentre("Mississauga Distribution Centre", 43.5890, -79.6441);
            DistributionCentre markham = new DistributionCentre("Markham Distribution Centre", 43.8561, -79.3370);
            
            distributionCentreRepository.save(northYork);
            distributionCentreRepository.save(scarborough);
            distributionCentreRepository.save(mississauga);
            distributionCentreRepository.save(markham);
            
            // Add items to North York centre
            Item item1 = new Item("Designer Jacket", Brand.BALENCIAGA, 2023, 1500.0, 10);
            item1.setDistributionCentre(northYork);
            itemRepository.save(item1);
            
            Item item2 = new Item("Luxury Sweater", Brand.STONE_ISLAND, 2023, 1200.0, 15);
            item2.setDistributionCentre(northYork);
            itemRepository.save(item2);
            
            Item item3 = new Item("Premium Bag", Brand.DIOR, 2022, 2500.0, 5);
            item3.setDistributionCentre(northYork);
            itemRepository.save(item3);
            
            // Add items to Scarborough centre
            Item item4 = new Item("Designer Shoes", Brand.GUCCI, 2023, 1800.0, 20);
            item4.setDistributionCentre(scarborough);
            itemRepository.save(item4);
            
            Item item5 = new Item("Luxury Watch", Brand.PRADA, 2022, 3000.0, 8);
            item5.setDistributionCentre(scarborough);
            itemRepository.save(item5);
            
            Item item6 = new Item("Designer Dress", Brand.VERSACE, 2023, 2200.0, 12);
            item6.setDistributionCentre(scarborough);
            itemRepository.save(item6);
            
            // Add items to Mississauga centre
            Item item7 = new Item("Premium Suit", Brand.ARMANI, 2022, 2800.0, 6);
            item7.setDistributionCentre(mississauga);
            itemRepository.save(item7);
            
            Item item8 = new Item("Luxury Perfume", Brand.CHANEL, 2023, 1100.0, 25);
            item8.setDistributionCentre(mississauga);
            itemRepository.save(item8);
            
            Item item9 = new Item("Designer Handbag", Brand.BALENCIAGA, 2023, 1900.0, 7);
            item9.setDistributionCentre(mississauga);
            itemRepository.save(item9);
            
            // Add items to Markham centre
            Item item10 = new Item("Luxury Sneakers", Brand.GUCCI, 2023, 1600.0, 18);
            item10.setDistributionCentre(markham);
            itemRepository.save(item10);
            
            Item item11 = new Item("Designer Scarf", Brand.DIOR, 2022, 1300.0, 14);
            item11.setDistributionCentre(markham);
            itemRepository.save(item11);
            
            Item item12 = new Item("Premium Sunglasses", Brand.PRADA, 2023, 1400.0, 9);
            item12.setDistributionCentre(markham);
            itemRepository.save(item12);
        }
    }
}

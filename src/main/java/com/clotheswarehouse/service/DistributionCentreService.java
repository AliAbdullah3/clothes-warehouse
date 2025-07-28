package com.clotheswarehouse.service;

import com.clotheswarehouse.dto.ItemRequestDto;
import com.clotheswarehouse.model.DistributionCentre;
import com.clotheswarehouse.model.DistributionCentreItem;
import com.clotheswarehouse.model.Item;
import com.clotheswarehouse.repository.DistributionCentreItemRepository;
import com.clotheswarehouse.repository.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DistributionCentreService {
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
    @Autowired
    private DistributionCentreItemRepository distributionCentreItemRepository;
    
    @Autowired
    private ItemService itemService;

    // GTA area coordinates (Toronto City Hall - CN Tower area)
    private static final double WAREHOUSE_LATITUDE = 43.6426;
    private static final double WAREHOUSE_LONGITUDE = -79.3871;
    
    public List<DistributionCentre> getAllDistributionCentres() {
        return distributionCentreRepository.findAllWithItems();
    }
    
    public Optional<DistributionCentre> getDistributionCentreById(Long id) {
        return distributionCentreRepository.findById(id);
    }
    
    public DistributionCentre saveDistributionCentre(DistributionCentre distributionCentre) {
        return distributionCentreRepository.save(distributionCentre);
    }
    
    public void deleteDistributionCentre(Long id) {
        distributionCentreRepository.deleteById(id);
    }
    
    public List<DistributionCentreItem> getItemsByDistributionCentre(Long centreId) {
        return distributionCentreItemRepository.findByDistributionCentreId(centreId);
    }
    
    public DistributionCentreItem saveDistributionCentreItem(DistributionCentreItem item) {
        return distributionCentreItemRepository.save(item);
    }
    
    public void deleteDistributionCentreItem(Long id) {
        distributionCentreItemRepository.deleteById(id);
    }
    
    @Transactional
    public DistributionCentreItem addItemToDistributionCentre(Long centreId, DistributionCentreItem item) {
        Optional<DistributionCentre> centreOpt = getDistributionCentreById(centreId);
        if (centreOpt.isPresent()) {
            DistributionCentre centre = centreOpt.get();
            item.setDistributionCentre(centre);
            return distributionCentreItemRepository.save(item);
        }
        throw new RuntimeException("Distribution centre not found");
    }

    @Transactional
    public String requestItemFromDistributionCentre(ItemRequestDto itemRequest) {
        try {
            List<DistributionCentre> centres = getAllDistributionCentres();
            DistributionCentre closestCentre = findClosestCentreWithItem(centres, itemRequest);

            if (closestCentre != null) {
                boolean success = requestItemFromCentre(closestCentre.getId(), itemRequest);
                
                if (success) {
                    addItemToWarehouse(itemRequest);
                    return "SUCCESS: Item successfully requested from " + closestCentre.getName() + 
                           " (Distance: " + String.format("%.2f", calculateDistance(
                               WAREHOUSE_LATITUDE, WAREHOUSE_LONGITUDE,
                               closestCentre.getLatitude(), closestCentre.getLongitude()
                           )) + " km)";
                }
            }
            return "ERROR: Item not available in any distribution centre";
        } catch (Exception e) {
            System.err.println("Error requesting item: " + e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }

    private DistributionCentre findClosestCentreWithItem(List<DistributionCentre> centres, ItemRequestDto itemRequest) {
        DistributionCentre closestCentre = null;
        double minDistance = Double.MAX_VALUE;

        System.out.println("🔍 Searching for item: " + itemRequest.getName() + " (" + itemRequest.getBrand() + ")");
        System.out.println("📍 Warehouse location: " + WAREHOUSE_LATITUDE + ", " + WAREHOUSE_LONGITUDE);

        for (DistributionCentre centre : centres) {
            boolean hasItem = centre.getItems().stream()
                    .anyMatch(item -> item.getName().equalsIgnoreCase(itemRequest.getName()) 
                            && item.getBrand() == itemRequest.getBrand() 
                            && item.getQuantity() > 0);

            double distance = calculateDistance(
                    WAREHOUSE_LATITUDE, WAREHOUSE_LONGITUDE,
                    centre.getLatitude(), centre.getLongitude()
            );

            System.out.println("🏢 " + centre.getName() + " (Distance: " + String.format("%.2f", distance) + " km) - Has item: " + hasItem);

            if (hasItem) {
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCentre = centre;
                }
            }
        }

        if (closestCentre != null) {
            System.out.println("✅ Closest centre selected: " + closestCentre.getName() + " (" + String.format("%.2f", minDistance) + " km)");
        } else {
            System.out.println("❌ No centre found with the requested item");
        }

        return closestCentre;
    }

    @Transactional
    public boolean requestItemFromCentre(Long centreId, ItemRequestDto itemRequest) {
        Optional<DistributionCentreItem> itemOpt = distributionCentreItemRepository.findAvailableItemInCentre(
            centreId, itemRequest.getName(), itemRequest.getBrand());
        
        if (itemOpt.isPresent()) {
            DistributionCentreItem item = itemOpt.get();
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                distributionCentreItemRepository.save(item);
                System.out.println("📦 Item deducted from distribution centre. Remaining quantity: " + item.getQuantity());
                return true;
            }
        }
        return false;
    }

    private void addItemToWarehouse(ItemRequestDto itemRequest) {
        List<Item> existingItems = itemService.findByNameAndBrand(itemRequest.getName(), itemRequest.getBrand());

        if (!existingItems.isEmpty()) {
            Item existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            itemService.saveItem(existingItem);
            System.out.println("📈 Warehouse stock updated. New quantity: " + existingItem.getQuantity());
        } else {
            Item newItem = new Item();
            newItem.setName(itemRequest.getName());
            newItem.setBrand(itemRequest.getBrand());
            newItem.setYearOfCreation(2023);
            newItem.setPrice(1500.0);
            newItem.setQuantity(1);
            itemService.saveItem(newItem);
            System.out.println("🆕 New item added to warehouse with quantity: 1");
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
}

package com.distributioncentre.service;

import com.distributioncentre.dto.ItemRequestDto;
import com.distributioncentre.model.DistributionCentre;
import com.distributioncentre.model.Item;
import com.distributioncentre.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;
    
    @Autowired
    private DistributionCentreService distributionCentreService;
    
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    public List<Item> getItemsByDistributionCentre(Long centreId) {
        return itemRepository.findByDistributionCentreId(centreId);
    }
    
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
    
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
    
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
    
    @Transactional
    public Item addItemToDistributionCentre(Long centreId, Item item) {
        Optional<DistributionCentre> centreOpt = distributionCentreService.getDistributionCentreById(centreId);
        if (centreOpt.isPresent()) {
            DistributionCentre centre = centreOpt.get();
            item.setDistributionCentre(centre);
            return itemRepository.save(item);
        }
        throw new RuntimeException("Distribution centre not found");
    }
    
    @Transactional
    public boolean requestItemFromCentre(Long centreId, ItemRequestDto itemRequest) {
        Optional<Item> itemOpt = itemRepository.findAvailableItemInCentre(
            centreId, itemRequest.getName(), itemRequest.getBrand());
        
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            if (item.getQuantity() > 0) {
                item.setQuantity(item.getQuantity() - 1);
                itemRepository.save(item);
                return true;
            }
        }
        return false;
    }
}

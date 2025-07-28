package com.clotheswarehouse.service;

import com.clotheswarehouse.model.Brand;
import com.clotheswarehouse.model.Item;
import com.clotheswarehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;
    
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    
    public Page<Item> getAllItemsPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return itemRepository.findAll(pageable);
    }
    
    public Page<Item> getAllItemsSortedByBrand(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return itemRepository.findAllByOrderByBrandAsc(pageable);
    }
    
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
    
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
    
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
    
    public List<Item> getItemsByBrandAndYear2022(Brand brand) {
        return itemRepository.findByBrandAndYear2022(brand);
    }
    
    public List<Item> getItemsByBrand(Brand brand) {
        return itemRepository.findByBrand(brand);
    }
    
    public List<Item> findByNameAndBrand(String name, Brand brand) {
        return itemRepository.findByNameIgnoreCaseAndBrand(name, brand);
    }
}

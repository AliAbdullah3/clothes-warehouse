package com.distributioncentre.controller;

import com.distributioncentre.dto.ItemRequestDto;
import com.distributioncentre.model.DistributionCentre;
import com.distributioncentre.model.Item;
import com.distributioncentre.service.DistributionCentreService;
import com.distributioncentre.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distribution-centres")
@CrossOrigin(origins = "*")
public class DistributionCentreController {
    
    @Autowired
    private DistributionCentreService distributionCentreService;
    
    @Autowired
    private ItemService itemService;
    
    @GetMapping
    public ResponseEntity<List<DistributionCentre>> getAllDistributionCentres() {
        List<DistributionCentre> centres = distributionCentreService.getAllDistributionCentres();
        return ResponseEntity.ok(centres);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DistributionCentre> getDistributionCentreById(@PathVariable Long id) {
        Optional<DistributionCentre> centre = distributionCentreService.getDistributionCentreById(id);
        return centre.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<DistributionCentre> createDistributionCentre(@Valid @RequestBody DistributionCentre distributionCentre) {
        DistributionCentre savedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCentre);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DistributionCentre> updateDistributionCentre(@PathVariable Long id, 
                                                                      @Valid @RequestBody DistributionCentre distributionCentre) {
        Optional<DistributionCentre> existingCentre = distributionCentreService.getDistributionCentreById(id);
        if (existingCentre.isPresent()) {
            distributionCentre.setId(id);
            DistributionCentre updatedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
            return ResponseEntity.ok(updatedCentre);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistributionCentre(@PathVariable Long id) {
        Optional<DistributionCentre> centre = distributionCentreService.getDistributionCentreById(id);
        if (centre.isPresent()) {
            distributionCentreService.deleteDistributionCentre(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/items")
    public ResponseEntity<Item> addItemToDistributionCentre(@PathVariable Long id, 
                                                           @Valid @RequestBody Item item) {
        try {
            Item savedItem = itemService.addItemToDistributionCentre(id, item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/items")
    public ResponseEntity<List<Item>> getItemsByDistributionCentre(@PathVariable Long id) {
        List<Item> items = itemService.getItemsByDistributionCentre(id);
        return ResponseEntity.ok(items);
    }
    
    @PostMapping("/{id}/request-item")
    public ResponseEntity<String> requestItemFromCentre(@PathVariable Long id, 
                                                       @Valid @RequestBody ItemRequestDto itemRequest) {
        boolean success = itemService.requestItemFromCentre(id, itemRequest);
        if (success) {
            return ResponseEntity.ok("Item requested successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not available");
        }
    }
    
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        Optional<Item> item = itemService.getItemById(itemId);
        if (item.isPresent()) {
            itemService.deleteItem(itemId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

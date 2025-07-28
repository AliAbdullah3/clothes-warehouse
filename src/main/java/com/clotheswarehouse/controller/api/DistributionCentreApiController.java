package com.clotheswarehouse.controller.api;

import com.clotheswarehouse.dto.ItemRequestDto;
import com.clotheswarehouse.model.DistributionCentre;
import com.clotheswarehouse.model.DistributionCentreItem;
import com.clotheswarehouse.service.DistributionCentreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/distribution-centres")
@CrossOrigin(origins = "*")
public class DistributionCentreApiController {
    
    @Autowired
    private DistributionCentreService distributionCentreService;
    
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DistributionCentre> createDistributionCentre(@Valid @RequestBody DistributionCentre distributionCentre) {
        DistributionCentre savedCentre = distributionCentreService.saveDistributionCentre(distributionCentre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCentre);
    }
    
    @PostMapping("/{id}/items")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DistributionCentreItem> addItemToDistributionCentre(@PathVariable Long id, 
                                                                             @Valid @RequestBody DistributionCentreItem item) {
        try {
            DistributionCentreItem savedItem = distributionCentreService.addItemToDistributionCentre(id, item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/items")
    public ResponseEntity<List<DistributionCentreItem>> getItemsByDistributionCentre(@PathVariable Long id) {
        List<DistributionCentreItem> items = distributionCentreService.getItemsByDistributionCentre(id);
        return ResponseEntity.ok(items);
    }
    
    @PostMapping("/{id}/request-item")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> requestItemFromCentre(@PathVariable Long id, 
                                                       @Valid @RequestBody ItemRequestDto itemRequest) {
        boolean success = distributionCentreService.requestItemFromCentre(id, itemRequest);
        if (success) {
            return ResponseEntity.ok("Item requested successfully from distribution centre " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not available in distribution centre " + id);
        }
    }
    
    @DeleteMapping("/items/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        try {
            distributionCentreService.deleteDistributionCentreItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

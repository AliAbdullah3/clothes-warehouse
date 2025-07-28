package com.distributioncentre.repository;

import com.distributioncentre.model.Brand;
import com.distributioncentre.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    List<Item> findByDistributionCentreId(Long distributionCentreId);
    
    @Query("SELECT i FROM Item i WHERE i.distributionCentre.id = :centreId AND i.name = :name AND i.brand = :brand AND i.quantity > 0")
    Optional<Item> findAvailableItemInCentre(@Param("centreId") Long centreId, 
                                           @Param("name") String name, 
                                           @Param("brand") Brand brand);
}

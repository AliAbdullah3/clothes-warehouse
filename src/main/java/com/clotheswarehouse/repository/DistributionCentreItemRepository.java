package com.clotheswarehouse.repository;

import com.clotheswarehouse.model.Brand;
import com.clotheswarehouse.model.DistributionCentreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistributionCentreItemRepository extends JpaRepository<DistributionCentreItem, Long> {
    
    List<DistributionCentreItem> findByDistributionCentreId(Long distributionCentreId);
    
    @Query("SELECT i FROM DistributionCentreItem i WHERE i.distributionCentre.id = :centreId AND i.name = :name AND i.brand = :brand AND i.quantity > 0")
    Optional<DistributionCentreItem> findAvailableItemInCentre(@Param("centreId") Long centreId, 
                                                              @Param("name") String name, 
                                                              @Param("brand") Brand brand);
}

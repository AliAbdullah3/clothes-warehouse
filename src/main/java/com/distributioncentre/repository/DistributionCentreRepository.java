package com.distributioncentre.repository;

import com.distributioncentre.model.DistributionCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionCentreRepository extends JpaRepository<DistributionCentre, Long> {
    
    @Query("SELECT DISTINCT dc FROM DistributionCentre dc LEFT JOIN FETCH dc.items")
    List<DistributionCentre> findAllWithItems();
}

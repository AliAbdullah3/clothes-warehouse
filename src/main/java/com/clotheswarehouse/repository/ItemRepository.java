package com.clotheswarehouse.repository;

import com.clotheswarehouse.model.Brand;
import com.clotheswarehouse.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.yearOfCreation = 2022")
    List<Item> findByBrandAndYear2022(@Param("brand") Brand brand);
    
    Page<Item> findAllByOrderByBrandAsc(Pageable pageable);
    
    List<Item> findByBrand(Brand brand);
    
    List<Item> findByNameIgnoreCaseAndBrand(String name, Brand brand);
}

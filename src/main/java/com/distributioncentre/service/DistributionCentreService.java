package com.distributioncentre.service;

import com.distributioncentre.model.DistributionCentre;
import com.distributioncentre.repository.DistributionCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistributionCentreService {
    
    @Autowired
    private DistributionCentreRepository distributionCentreRepository;
    
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
}

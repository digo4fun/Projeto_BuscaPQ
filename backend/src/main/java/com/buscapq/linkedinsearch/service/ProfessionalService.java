package com.buscapq.linkedinsearch.service;

import com.buscapq.linkedinsearch.dto.LinkedinProfile;
import com.buscapq.linkedinsearch.model.Professional;
import com.buscapq.linkedinsearch.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    
    @Autowired
    private ProfessionalRepository professionalRepository;
    
    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAllOrderBySearchDateDesc();
    }
    
    public Optional<Professional> getProfessionalById(Long id) {
        return professionalRepository.findById(id);
    }
    
    public Professional saveProfessional(LinkedinProfile linkedinProfile, String searchedProfile) {
        Optional<Professional> existingProfessional = professionalRepository.findByLinkedinUrl(linkedinProfile.getLinkedinUrl());
        
        if (existingProfessional.isPresent()) {
            return existingProfessional.get();
        }
        
        Professional professional = new Professional();
        professional.setName(linkedinProfile.getName());
        professional.setLinkedinUrl(linkedinProfile.getLinkedinUrl());
        professional.setSearchedProfile(searchedProfile);
        professional.setSearchDate(LocalDateTime.now());
        professional.setSkills(linkedinProfile.getSkills());
        professional.setLocation(linkedinProfile.getLocation());
        professional.setExperience(linkedinProfile.getExperience());
        professional.setTitle(linkedinProfile.getTitle());
        professional.setCompany(linkedinProfile.getCompany());
        
        return professionalRepository.save(professional);
    }
    
    public void deleteProfessional(Long id) {
        professionalRepository.deleteById(id);
    }
    
    public List<Professional> getProfessionalsByProfile(String profile) {
        return professionalRepository.findBySearchedProfileContaining(profile);
    }
}

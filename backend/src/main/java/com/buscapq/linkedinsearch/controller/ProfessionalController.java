package com.buscapq.linkedinsearch.controller;

import com.buscapq.linkedinsearch.dto.LinkedinProfile;
import com.buscapq.linkedinsearch.model.Professional;
import com.buscapq.linkedinsearch.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/professionals")
@CrossOrigin(origins = "*")
public class ProfessionalController {
    
    @Autowired
    private ProfessionalService professionalService;
    
    @GetMapping
    public ResponseEntity<List<Professional>> getAllProfessionals() {
        List<Professional> professionals = professionalService.getAllProfessionals();
        return ResponseEntity.ok(professionals);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professional> getProfessionalById(@PathVariable Long id) {
        Optional<Professional> professional = professionalService.getProfessionalById(id);
        return professional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Professional> saveProfessional(@RequestBody LinkedinProfile linkedinProfile, 
                                                        @RequestParam String searchedProfile) {
        try {
            Professional savedProfessional = professionalService.saveProfessional(linkedinProfile, searchedProfile);
            return ResponseEntity.ok(savedProfessional);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        try {
            professionalService.deleteProfessional(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Professional>> getProfessionalsByProfile(@RequestParam String profile) {
        List<Professional> professionals = professionalService.getProfessionalsByProfile(profile);
        return ResponseEntity.ok(professionals);
    }
}

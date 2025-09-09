package com.buscapq.linkedinsearch.controller;

import com.buscapq.linkedinsearch.dto.LinkedinProfile;
import com.buscapq.linkedinsearch.dto.SearchRequest;
import com.buscapq.linkedinsearch.service.LinkedinSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {
    
    @Autowired
    private LinkedinSearchService linkedinSearchService;
    
    @PostMapping("/profiles")
    public ResponseEntity<List<LinkedinProfile>> searchProfiles(@Valid @RequestBody SearchRequest searchRequest) {
        try {
            List<LinkedinProfile> profiles = linkedinSearchService.searchProfiles(searchRequest);
            return ResponseEntity.ok(profiles);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

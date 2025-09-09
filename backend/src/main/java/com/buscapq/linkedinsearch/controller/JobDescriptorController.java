package com.buscapq.linkedinsearch.controller;

import com.buscapq.linkedinsearch.dto.JobDescriptorRequest;
import com.buscapq.linkedinsearch.dto.JobDescriptorResponse;
import com.buscapq.linkedinsearch.service.JobDescriptorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-descriptor")
@CrossOrigin(origins = "*")
public class JobDescriptorController {
    
    @Autowired
    private JobDescriptorService jobDescriptorService;
    
    @PostMapping("/generate")
    public ResponseEntity<JobDescriptorResponse> generateJobDescription(@Valid @RequestBody JobDescriptorRequest request) {
        try {
            JobDescriptorResponse response = jobDescriptorService.generateJobDescription(request);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.internalServerError().body(response);
            }
            
        } catch (Exception e) {
            JobDescriptorResponse errorResponse = new JobDescriptorResponse(null, false, "Erro interno do servidor");
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}

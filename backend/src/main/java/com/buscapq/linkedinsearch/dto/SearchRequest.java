package com.buscapq.linkedinsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

public class SearchRequest {
    
    @NotBlank(message = "Skills são obrigatórias")
    private String skills;
    
    @Min(value = 0, message = "Anos de experiência deve ser maior ou igual a 0")
    private Integer yearsOfExperience;
    
    @NotBlank(message = "Localização é obrigatória")
    private String location;
    
    public SearchRequest() {}
    
    public SearchRequest(String skills, Integer yearsOfExperience, String location) {
        this.skills = skills;
        this.yearsOfExperience = yearsOfExperience;
        this.location = location;
    }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

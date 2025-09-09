package com.buscapq.linkedinsearch.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "professionals")
public class Professional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Link do LinkedIn é obrigatório")
    @Column(nullable = false, unique = true)
    private String linkedinUrl;
    
    @NotBlank(message = "Perfil buscado é obrigatório")
    @Column(nullable = false)
    private String searchedProfile;
    
    @NotNull(message = "Data da busca é obrigatória")
    @Column(nullable = false)
    private LocalDateTime searchDate;
    
    private String skills;
    private String location;
    private String experience;
    private String title;
    private String company;
    
    public Professional() {}
    
    public Professional(String name, String linkedinUrl, String searchedProfile, 
                       LocalDateTime searchDate, String skills, String location, 
                       String experience, String title, String company) {
        this.name = name;
        this.linkedinUrl = linkedinUrl;
        this.searchedProfile = searchedProfile;
        this.searchDate = searchDate;
        this.skills = skills;
        this.location = location;
        this.experience = experience;
        this.title = title;
        this.company = company;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    
    public String getSearchedProfile() { return searchedProfile; }
    public void setSearchedProfile(String searchedProfile) { this.searchedProfile = searchedProfile; }
    
    public LocalDateTime getSearchDate() { return searchDate; }
    public void setSearchDate(LocalDateTime searchDate) { this.searchDate = searchDate; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
}

package com.buscapq.linkedinsearch.dto;

public class LinkedinProfile {
    
    private String name;
    private String title;
    private String company;
    private String location;
    private String linkedinUrl;
    private String experience;
    private String skills;
    
    public LinkedinProfile() {}
    
    public LinkedinProfile(String name, String title, String company, String location, 
                          String linkedinUrl, String experience, String skills) {
        this.name = name;
        this.title = title;
        this.company = company;
        this.location = location;
        this.linkedinUrl = linkedinUrl;
        this.experience = experience;
        this.skills = skills;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
}

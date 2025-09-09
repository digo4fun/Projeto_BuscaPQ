package com.buscapq.linkedinsearch.dto;

import jakarta.validation.constraints.NotBlank;

public class JobDescriptorRequest {
    
    @NotBlank(message = "Perfil principal é obrigatório")
    private String primarySkill;
    
    private String secondarySkill;
    
    private String otherSkills;
    
    @NotBlank(message = "Senioridade é obrigatória")
    private String seniority;
    
    public JobDescriptorRequest() {}
    
    public JobDescriptorRequest(String primarySkill, String secondarySkill, String otherSkills, String seniority) {
        this.primarySkill = primarySkill;
        this.secondarySkill = secondarySkill;
        this.otherSkills = otherSkills;
        this.seniority = seniority;
    }
    
    public String getPrimarySkill() { return primarySkill; }
    public void setPrimarySkill(String primarySkill) { this.primarySkill = primarySkill; }
    
    public String getSecondarySkill() { return secondarySkill; }
    public void setSecondarySkill(String secondarySkill) { this.secondarySkill = secondarySkill; }
    
    public String getOtherSkills() { return otherSkills; }
    public void setOtherSkills(String otherSkills) { this.otherSkills = otherSkills; }
    
    public String getSeniority() { return seniority; }
    public void setSeniority(String seniority) { this.seniority = seniority; }
}

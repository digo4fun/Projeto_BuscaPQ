package com.buscapq.linkedinsearch.dto;

public class JobDescriptorResponse {
    
    private String jobDescription;
    private boolean success;
    private String errorMessage;
    
    public JobDescriptorResponse() {}
    
    public JobDescriptorResponse(String jobDescription, boolean success, String errorMessage) {
        this.jobDescription = jobDescription;
        this.success = success;
        this.errorMessage = errorMessage;
    }
    
    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}

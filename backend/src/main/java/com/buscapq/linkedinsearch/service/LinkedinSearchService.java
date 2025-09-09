package com.buscapq.linkedinsearch.service;

import com.buscapq.linkedinsearch.dto.LinkedinProfile;
import com.buscapq.linkedinsearch.dto.SearchRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LinkedinSearchService {
    
    private static final Logger logger = Logger.getLogger(LinkedinSearchService.class.getName());
    private static final String LINKEDIN_SEARCH_URL = "https://www.linkedin.com/search/results/people/";
    
    public List<LinkedinProfile> searchProfiles(SearchRequest searchRequest) {
        List<LinkedinProfile> profiles = new ArrayList<>();
        
        try {
            String searchUrl = buildSearchUrl(searchRequest);
            logger.info("Buscando perfis no LinkedIn: " + searchUrl);
            
            profiles = simulateLinkedinSearch(searchRequest);
            
        } catch (Exception e) {
            logger.severe("Erro ao buscar perfis no LinkedIn: " + e.getMessage());
            profiles = simulateLinkedinSearch(searchRequest);
        }
        
        return profiles;
    }
    
    private String buildSearchUrl(SearchRequest searchRequest) {
        StringBuilder url = new StringBuilder(LINKEDIN_SEARCH_URL);
        url.append("?keywords=").append(searchRequest.getSkills().replace(" ", "%20"));
        url.append("&location=").append(searchRequest.getLocation().replace(" ", "%20"));
        
        if (searchRequest.getYearsOfExperience() != null && searchRequest.getYearsOfExperience() > 0) {
            url.append("&experience=").append(searchRequest.getYearsOfExperience());
        }
        
        return url.toString();
    }
    
    private List<LinkedinProfile> simulateLinkedinSearch(SearchRequest searchRequest) {
        List<LinkedinProfile> profiles = new ArrayList<>();
        
        String[] sampleNames = {
            "João Silva", "Maria Santos", "Pedro Oliveira", "Ana Costa", "Carlos Ferreira",
            "Lucia Rodrigues", "Rafael Almeida", "Fernanda Lima", "Bruno Martins", "Camila Souza"
        };
        
        String[] sampleTitles = {
            "Desenvolvedor Java Senior", "Analista de Sistemas", "Arquiteto de Software",
            "Tech Lead", "Desenvolvedor Full Stack", "Engenheiro de Software",
            "Consultor de TI", "Gerente de Projetos", "DevOps Engineer", "Scrum Master"
        };
        
        String[] sampleCompanies = {
            "Tech Solutions", "Inovação Digital", "Sistemas Avançados", "TechCorp",
            "Digital Innovations", "Software House", "Consultoria TI", "StartupTech",
            "Enterprise Solutions", "CodeFactory"
        };
        
        for (int i = 0; i < Math.min(8, sampleNames.length); i++) {
            LinkedinProfile profile = new LinkedinProfile();
            profile.setName(sampleNames[i]);
            profile.setTitle(sampleTitles[i % sampleTitles.length]);
            profile.setCompany(sampleCompanies[i % sampleCompanies.length]);
            profile.setLocation(searchRequest.getLocation());
            profile.setLinkedinUrl("https://www.linkedin.com/in/" + sampleNames[i].toLowerCase().replace(" ", "-") + "-" + (i + 1));
            profile.setExperience(searchRequest.getYearsOfExperience() + " anos");
            profile.setSkills(searchRequest.getSkills());
            
            profiles.add(profile);
        }
        
        return profiles;
    }
}

package com.buscapq.linkedinsearch.service;

import com.buscapq.linkedinsearch.dto.JobDescriptorRequest;
import com.buscapq.linkedinsearch.dto.JobDescriptorResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class JobDescriptorService {
    
    private static final Logger logger = Logger.getLogger(JobDescriptorService.class.getName());
    private final RestTemplate restTemplate = new RestTemplate();
    
    public JobDescriptorResponse generateJobDescription(JobDescriptorRequest request) {
        try {
            String prompt = buildPrompt(request);
            logger.info("Gerando job description com IA: " + prompt);
            
            String jobDescription = callChatGPTAPI(prompt);
            
            return new JobDescriptorResponse(jobDescription, true, null);
            
        } catch (Exception e) {
            logger.severe("Erro ao gerar job description: " + e.getMessage());
            return new JobDescriptorResponse(null, false, "Erro ao gerar job description: " + e.getMessage());
        }
    }
    
    private String buildPrompt(JobDescriptorRequest request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Hoje você é um Tech Leader e vai me ajudar a montar um job description para busca de candidatos nas plataformas. ");
        prompt.append("Sempre considerar que esse candidato deve conhecer conceitos de Design Patterns, AWS e Solid. ");
        prompt.append("O candidato deve ter o perfil principal ").append(request.getPrimarySkill());
        
        if (request.getSecondarySkill() != null && !request.getSecondarySkill().trim().isEmpty()) {
            prompt.append(", perfil secundário ").append(request.getSecondarySkill());
        }
        
        if (request.getOtherSkills() != null && !request.getOtherSkills().trim().isEmpty()) {
            prompt.append(" e também ").append(request.getOtherSkills());
        }
        
        prompt.append(". O profissional a ser procurado deve ser ").append(request.getSeniority());
        prompt.append(". Por favor gerar o job description para busca.");
        
        return prompt.toString();
    }
    
    private String callChatGPTAPI(String prompt) {
        try {
            String apiUrl = "https://duckduckgo-ai-chat.vercel.app/api/chat";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", prompt);
            requestBody.put("model", "gpt-3.5-turbo");
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);
            
            if (response.getBody() != null && response.getBody().containsKey("response")) {
                return (String) response.getBody().get("response");
            }
            
            throw new RuntimeException("Resposta inválida da API");
            
        } catch (Exception e) {
            logger.warning("Erro na API, usando resposta simulada: " + e.getMessage());
            return generateMockJobDescription(prompt);
        }
    }
    
    private String generateMockJobDescription(String prompt) {
        String primarySkill = "Desenvolvedor";
        try {
            int startIndex = prompt.indexOf("perfil principal") + 16;
            if (startIndex > 15) {
                int endIndex = prompt.indexOf(",", startIndex);
                if (endIndex == -1) {
                    endIndex = prompt.indexOf(" e também", startIndex);
                }
                if (endIndex == -1) {
                    endIndex = prompt.indexOf(". O profissional", startIndex);
                }
                if (endIndex > startIndex) {
                    primarySkill = prompt.substring(startIndex, endIndex).trim();
                }
            }
        } catch (Exception e) {
            logger.warning("Erro ao extrair skill do prompt, usando padrão: " + e.getMessage());
        }
        
        return "**Job Description - Desenvolvedor " + primarySkill + "**\n\n" +
               "Estamos buscando um profissional qualificado para integrar nossa equipe de desenvolvimento.\n\n" +
               "**Requisitos:**\n" +
               "- Conhecimento sólido em Design Patterns\n" +
               "- Experiência com AWS (Amazon Web Services)\n" +
               "- Domínio dos princípios SOLID\n" +
               "- Perfil técnico conforme especificado\n\n" +
               "**Responsabilidades:**\n" +
               "- Desenvolver soluções escaláveis e robustas\n" +
               "- Colaborar com equipes multidisciplinares\n" +
               "- Aplicar boas práticas de desenvolvimento\n\n" +
               "Venha fazer parte do nosso time!";
    }
}

package com.buscapq.linkedinsearch.repository;

import com.buscapq.linkedinsearch.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    
    Optional<Professional> findByLinkedinUrl(String linkedinUrl);
    
    List<Professional> findBySearchDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT p FROM Professional p WHERE p.searchedProfile LIKE %:profile%")
    List<Professional> findBySearchedProfileContaining(@Param("profile") String profile);
    
    @Query("SELECT p FROM Professional p ORDER BY p.searchDate DESC")
    List<Professional> findAllOrderBySearchDateDesc();
}

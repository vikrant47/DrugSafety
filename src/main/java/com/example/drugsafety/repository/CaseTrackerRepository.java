package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.CaseTracker;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;

public interface CaseTrackerRepository extends JpaRepository<CaseTracker, String> {

    @Query
    public List<CaseTracker> findPendingCaseOrderByPriority();
}

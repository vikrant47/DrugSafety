package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.CaseTracker;
import com.example.drugsafety.entity.DrugData;
import com.example.drugsafety.entity.acl.Role;

public interface CaseTrackerRepository extends JpaRepository<CaseTracker, String>{
}

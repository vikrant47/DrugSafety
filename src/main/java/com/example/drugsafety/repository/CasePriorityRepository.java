package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.CaseCategory;
import com.example.drugsafety.entity.DrugData;
import com.example.drugsafety.entity.acl.Role;

public interface CasePriorityRepository extends JpaRepository<CaseCategory, String>{
}

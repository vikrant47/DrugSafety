package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.CaseCategory;

public interface CaseCategoryRepository extends JpaRepository<CaseCategory, String>{
}

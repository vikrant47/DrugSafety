package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.DrugData;
import com.example.drugsafety.entity.acl.Role;

public interface DrugDataRepository extends JpaRepository<DrugData, Integer>{
	public Role findByName(String name);
	public Role findByGenericName(String genericName);
}

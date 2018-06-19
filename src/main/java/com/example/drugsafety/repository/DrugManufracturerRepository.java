package com.example.drugsafety.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.drugsafety.entity.DrugData;
import com.example.drugsafety.entity.DrugManufracturer;
import com.example.drugsafety.entity.acl.Role;

public interface DrugManufracturerRepository extends JpaRepository<DrugManufracturer, Integer>{
	public Role findByName(String name);
}

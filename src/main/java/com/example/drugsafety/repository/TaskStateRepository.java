package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.CaseCategory;
import com.example.drugsafety.entity.TaskState;

public interface TaskStateRepository extends JpaRepository<TaskState, String>{
}

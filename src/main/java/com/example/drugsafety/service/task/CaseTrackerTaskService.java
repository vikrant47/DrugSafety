/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.service.task;

import com.example.drugsafety.service.CrudService;
import com.example.drugsafety.service.TaskStateService;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SS
 */
@Service
public class CaseTrackerTaskService extends AbstractTaskService {

    @Autowired
    public CaseTrackerTaskService(EntityManager em, TaskStateService taskStateService, CrudService crudService) {
        super(em, taskStateService, crudService);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.service;

import com.example.drugsafety.entity.CaseCategory;
import com.example.drugsafety.entity.CaseTracker;
import com.example.drugsafety.repository.CaseTrackerRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SS
 */
@Service
public class CaseTrackerService {

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;
    @Autowired
    CaseTrackerRepository caseTrackerRepository;

    public void prioritizeCase(CaseTracker caseTracker) {
        CaseCategory caseCategory = caseTracker.getCaseCategory();
        caseTracker.setPriority(caseCategory.getPriority());

    }

    public CaseTracker newCaseTracker(CaseTracker caseTracker, CaseCategory category) {
        caseTracker.setCaseCategory(category);
        this.prioritizeCase(caseTracker);
        this.em.persist(caseTracker);
        return caseTracker;
    }

    public CaseTracker newCaseTracker(CaseTracker caseTracker, String caseCategoryId) {
        if (caseCategoryId != null) {
            CaseCategory category = em.find(CaseCategory.class, caseCategoryId);
            return this.newCaseTracker(caseTracker, category);
        }
        return null;
    }

    public List<CaseTracker> getPendingCaseOrderByPriority() {
        return this.caseTrackerRepository.findPendingCaseOrderByPriority();
    }

    public CaseTracker pickCase(CaseTracker caseTracker) {
        caseTracker.setStartedAt(new Date());
        return caseTracker;
    }

}

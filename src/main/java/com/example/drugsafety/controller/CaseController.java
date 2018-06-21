package com.example.drugsafety.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.drugsafety.entity.CaseCategory;
import com.example.drugsafety.entity.CaseTracker;
import com.example.drugsafety.model.ResponseModel;
import com.example.drugsafety.repository.CaseTrackerRepository;
import com.example.drugsafety.service.util.ResponseService;
import com.example.drugsafety.repository.CaseCategoryRepository;

@RequestMapping("/api/admin")
@Controller
public class CaseController {

    private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

    @Autowired
    private ResponseService responseService;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CaseCategoryRepository caseCategoryRepository;

    private CaseTrackerRepository caseTrackerRepository;

    @RequestMapping(value = "/case-category/new", method = RequestMethod.POST)
    @Transactional
    public ResponseModel addNewCaseCategory(@Valid CaseCategory data) {
        this.entityManager.persist(data);
        this.entityManager.flush();
        return this.responseService.response(data);
    }

    @RequestMapping(value = "/case-category/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseModel updateCaseCategory(@PathVariable("id") String id, @Valid CaseCategory data) {
        CaseCategory caseCategory = this.entityManager.find(CaseCategory.class, id);
        if (caseCategory == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        this.entityManager.merge(data);
        this.entityManager.persist(caseCategory);
        this.entityManager.flush();
        return this.responseService.response(caseCategory);
    }

    @RequestMapping(value = "/case-category/{id}", method = RequestMethod.DELETE)
    public ResponseModel deleteCaseCategoryById(@PathVariable("id") String id) {
        CaseCategory caseCategory = this.entityManager.find(CaseCategory.class, id);
        if (caseCategory == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        this.entityManager.remove(caseCategory);
        this.entityManager.flush();
        return this.responseService.response(caseCategory);

    }

    @RequestMapping(value = "/case-priorities", method = RequestMethod.GET)
    public ResponseModel getAllCasePriorities() {
        return this.responseService.response(this.caseCategoryRepository.findAll());
    }

    @RequestMapping(value = "/case-category/{id}", method = RequestMethod.GET)
    public ResponseModel getUserById(@PathVariable("id") String id) {
        return this.responseService.response(this.caseCategoryRepository.findById(id)).notFoundIfNull();
    }

    @RequestMapping(value = "/case-tracker/new", method = RequestMethod.POST)
    @Transactional
    public ResponseModel addNewCaseTracker(@Valid CaseTracker data) {
        this.entityManager.persist(data);
        this.entityManager.flush();
        return this.responseService.response(data);
    }

    @RequestMapping(value = "/case-tracker/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseModel updateCaseTracker(@PathVariable("id") String id, @Valid CaseTracker data) {
        CaseTracker caseTracker = this.entityManager.find(CaseTracker.class, id);
        if (caseTracker == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        this.entityManager.merge(data);
        this.entityManager.persist(caseTracker);
        this.entityManager.flush();
        return this.responseService.response(caseTracker);
    }

    @RequestMapping(value = "/case-tracker/{id}", method = RequestMethod.DELETE)
    public ResponseModel deleteCaseTrackerById(@PathVariable("id") String id) {
        CaseTracker caseTracker = this.entityManager.find(CaseTracker.class, id);
        if (caseTracker == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        this.entityManager.remove(caseTracker);
        this.entityManager.flush();
        return this.responseService.response(caseTracker);

    }

    @RequestMapping(value = "/case-trackers", method = RequestMethod.GET)
    public ResponseModel getAllManufracturer() {
        return this.responseService.response(this.caseTrackerRepository.findAll());
    }

    public ResponseModel getManufracturerById(@PathVariable("id") String id) {
        return this.responseService.response(this.caseTrackerRepository.findById(id)).notFoundIfNull();

    }
}

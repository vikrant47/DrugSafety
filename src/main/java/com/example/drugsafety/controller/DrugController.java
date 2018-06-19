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

import com.example.drugsafety.entity.DrugData;
import com.example.drugsafety.entity.DrugManufracturer;
import com.example.drugsafety.model.ResponseModel;
import com.example.drugsafety.repository.DrugDataRepository;
import com.example.drugsafety.repository.DrugManufracturerRepository;
import com.example.drugsafety.service.util.ResponseService;

@RequestMapping("/api/admin")
@Controller
public class DrugController {
	private static final Logger logger = LoggerFactory.getLogger(DrugController.class);

	@Autowired
	private ResponseService responseService;

	@Autowired
	private EntityManager entityManager;
	@Autowired
	private DrugDataRepository drugDataRepository;

	private DrugManufracturerRepository drugManufracturerRepository;
	@RequestMapping(value = "/drug-data/new", method = RequestMethod.POST)
	@Transactional
	public ResponseModel addNewDrugData(@Valid DrugData data) {
		this.entityManager.persist(data);
		this.entityManager.flush();
		return this.responseService.response(data);
	}

	@RequestMapping(value = "/drug-data/{id}", method = RequestMethod.PUT)
	@Transactional
	public ResponseModel updateDrugData(@PathVariable("id") String id, @Valid DrugData data) {
		DrugData drugData = this.entityManager.find(DrugData.class, id);
		if (drugData == null) {
			return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
		}
		this.entityManager.merge(data);
		this.entityManager.persist(drugData);
		this.entityManager.flush();
		return this.responseService.response(drugData);
	}

	@RequestMapping(value = "/drug-data/{id}", method = RequestMethod.DELETE)
	public ResponseModel deleteDrugDataById(@PathVariable("id") String id) {
		DrugData drugData = this.entityManager.find(DrugData.class, id);
		if (drugData == null) {
			return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
		}
		this.entityManager.remove(drugData);
		this.entityManager.flush();
		return this.responseService.response(drugData);

	}

	@RequestMapping(value = "/drugs", method = RequestMethod.GET)
	public ResponseModel getAllUser() {
		return this.responseService.response(this.drugDataRepository.findAll());
	}

	@RequestMapping(value = "/drug-data/{id}", method = RequestMethod.GET)
	public ResponseModel getUserById(@PathVariable("id") int id) {
		return this.responseService.response(this.drugDataRepository.findById(id)).notFoundIfNull();

	}

	@RequestMapping(value = "/drug-manufracturer/new", method = RequestMethod.POST)
	@Transactional
	public ResponseModel addNewDrugManufracturer(@Valid DrugManufracturer data) {
		this.entityManager.persist(data);
		this.entityManager.flush();
		return this.responseService.response(data);
	}
 
	@RequestMapping(value = "/drug-manufracturer/{id}", method = RequestMethod.PUT)
	@Transactional
	public ResponseModel updateDrugManufracturer(@PathVariable("id") String id, @Valid DrugManufracturer data) {
		DrugManufracturer drugManufracturer = this.entityManager.find(DrugManufracturer.class, id);
		if (drugManufracturer == null) {
			return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
		}
		this.entityManager.merge(data);
		this.entityManager.persist(drugManufracturer);
		this.entityManager.flush();
		return this.responseService.response(drugManufracturer);
	}

	@RequestMapping(value = "/drug-manufracturer/{id}", method = RequestMethod.DELETE)
	public ResponseModel deleteDrugManufracturerById(@PathVariable("id") String id) {
		DrugManufracturer drugManufracturer = this.entityManager.find(DrugManufracturer.class, id);
		if (drugManufracturer == null) {
			return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
		}
		this.entityManager.remove(drugManufracturer);
		this.entityManager.flush();
		return this.responseService.response(drugManufracturer);

	}

	@RequestMapping(value = "/drug-manufracturers", method = RequestMethod.GET)
	public ResponseModel getAllManufracturer() {
		return this.responseService.response(this.drugManufracturerRepository.findAll());
	}

	@RequestMapping(value = "/drug-manufracturer/{id}", method = RequestMethod.GET)
	public ResponseModel getManufracturerById(@PathVariable("id") int id) {
		return this.responseService.response(this.drugManufracturerRepository.findById(id)).notFoundIfNull();

	}
}

package com.example.drugsafety.controller;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.drugsafety.entity.acl.Setting;
import com.example.drugsafety.model.ResponseModel;
import com.example.drugsafety.repository.SettingRepository;
import com.example.drugsafety.service.CrudService;
import com.example.drugsafety.service.SettingService;
import com.example.drugsafety.service.util.ResponseService;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class SettingController {

    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);

    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingRepository settingRepository;
    @Autowired
    private EntityManager em;

    @Autowired
    private CrudService crudService;
    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ResponseModel getAllSetting() {
        return this.responseService.response(this.settingRepository.findActive());
    }

    @RequestMapping(value = "/setting/{id}", method = RequestMethod.GET)
    public ResponseModel getSettingById(@PathVariable("id") String id) {
        return this.responseService.response(this.crudService.findById(Setting.class, id)).notFoundIfNull();

    }

    @RequestMapping(value = "/setting/{name}", method = RequestMethod.GET)
    public ResponseModel getSettingByName(@PathVariable("name") String name) {
        List<Setting> settings = this.settingRepository.findActiveByName(name);
        if (settings != null && settings.size() > 0) {
            return this.responseService.response(settings.get(0));
        }
        return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/setting/new", method = RequestMethod.POST)
    @Transactional
    public ResponseModel addNewSetting(@Valid Setting data) {
        List<Setting> settings = this.settingRepository.findActiveByName(data.getName());
        if (settings != null && settings.size() > 0) {
            return this.responseService.response(settings.get(0)).statusCode(HttpStatus.BAD_REQUEST).action()
                    .error("message.error", "setting.error.duplicate").response();
        }
        this.settingRepository.save(data);
        return this.responseService.response(data);
    }

    @RequestMapping(value = "/setting/{id}", method = RequestMethod.PUT)
    @Transactional
    public ResponseModel updateSetting(@PathVariable("id") String id, @Valid Setting data) {
        Setting setting = this.crudService.update(Setting.class, id, data);
        if (setting == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        return this.responseService.response(setting);
    }

    @RequestMapping(value = "/setting/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseModel deleteSettingById(@PathVariable("id") String id) {
        Setting setting = this.crudService.remove(Setting.class, id);
        if (setting == null) {
            return this.responseService.response().statusCode(HttpStatus.NOT_FOUND);
        }
        return this.responseService.response(setting);

    }

    @RequestMapping(value = "/setting/{id}/activate", method = RequestMethod.POST)
    @Transactional
    public ResponseModel activateSetting(@PathVariable("id") String id) {
        Setting setting = this.crudService.findById(Setting.class, id);
        setting.setActive(true);
        this.em.persist(setting);
        return this.responseService.response(this.crudService.findById(Setting.class, id)).notFoundIfNull();

    }

    @RequestMapping(value = "/setting/{id}/deactivate", method = RequestMethod.POST)
    @Transactional
    public ResponseModel deactivateSetting(@PathVariable("id") String id) {
        Setting setting = this.crudService.findById(Setting.class, id);
        setting.setActive(false);
        this.em.persist(setting);
        return this.responseService.response(this.crudService.findById(Setting.class, id)).notFoundIfNull();

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.util;

import com.example.drugsafety.controller.AdminController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author SS
 */
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            logger.error("Unexpected IOEx encoding object to json: " + meta);
        }
        return null;
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Unexpected IOEx decoding json from database: " + dbData);
            return null;
        }
    }

}

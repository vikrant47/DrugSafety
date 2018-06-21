/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.entity.acl;

import com.example.drugsafety.util.BeanProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author SS
 */
@Entity
@Table(name = "setting")
@NamedQueries({
    @NamedQuery(name = "ProjSetting.findAll", query = "SELECT p FROM ProjSetting p")
    , @NamedQuery(name = "ProjSetting.findById", query = "SELECT p FROM ProjSetting p WHERE p.id = :id")
    , @NamedQuery(name = "ProjSetting.findByName", query = "SELECT p FROM ProjSetting p WHERE p.name = :name")
    , @NamedQuery(name = "ProjSetting.findByDataType", query = "SELECT p FROM ProjSetting p WHERE p.dataType = :dataType")
    , @NamedQuery(name = "ProjSetting.findActive", query = "SELECT p FROM ProjSetting p WHERE p.active = true")
    , @NamedQuery(name = "ProjSetting.findActiveByName", query = "SELECT p FROM ProjSetting p WHERE p.active = true and p.name = :name")})
public class Setting implements Serializable {

    public static final String COMPANY_NAME = "COMPANY_NAME";
    public static final String COMPANY_LOGO = "COMPANY_LOGO";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Size(max = 45)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "small_value")
    private String smallValue;

    @Lob
    @Size(max = 65535)
    @Column(name = "large_value")
    private String largeValue;

    @Size(max = 45)
    @Column(name = "data_type")
    private String dataType;

    @Column(name = "active")
    private boolean active;

    @Transient
    private Object value;

    public Setting() {
    }

    public Setting(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallValue() {
        return smallValue;
    }

    public void setSmallValue(String smallValue) {
        this.smallValue = smallValue;
    }

    public String getLargeValue() {
        return largeValue;
    }

    public void setLargeValue(String largeValue) {
        this.largeValue = largeValue;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Object getValue() {
        try {
            String val = this.getSmallValue() == null ? this.getLargeValue() : this.getSmallValue();
            if (this.value == null && val != null) {
                if (this.getDataType() != null) {
                    Class type = Class.forName(this.getDataType());
                    if (BeanProcessor.isWrapperType(type)) {
                        this.value = BeanProcessor.stringToWrapper(type, val);
                    } else {
                        this.value = mapper.readValue(val, type);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public void setValue(Object value) {
        if (value != null) {
            if (value instanceof String) {
                if (value.toString().length() < 255) {
                    this.setSmallValue(value.toString());
                } else {
                    this.setLargeValue(value.toString());
                }
            } else if (BeanProcessor.isWrapperType(value.getClass())) {
                this.setSmallValue(value.toString());
            } else {
                try {
                    this.setLargeValue(mapper.writeValueAsString(value));
                } catch (JsonProcessingException ex) {
                    this.setLargeValue(value.toString());
                    Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.setDataType(value.getClass().getName());
        }
        //Enable re-loading of value after change
        this.value = null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setting)) {
            return false;
        }
        Setting other = (Setting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.drugsafety.entity.acl.ProjSetting[ id=" + id + " ]";
    }

}

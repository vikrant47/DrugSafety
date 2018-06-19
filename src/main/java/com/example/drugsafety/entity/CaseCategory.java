/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.entity;

import com.example.drugsafety.entity.acl.User;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author SS
 */
@Entity
@Table(name = "case_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaseCategory.findAll", query = "SELECT c FROM CaseCategory c")
    , @NamedQuery(name = "CaseCategory.findById", query = "SELECT c FROM CaseCategory c WHERE c.id = :id")
    , @NamedQuery(name = "CaseCategory.findByName", query = "SELECT c FROM CaseCategory c WHERE c.name = :name")
    , @NamedQuery(name = "CaseCategory.findByPriority", query = "SELECT c FROM CaseCategory c WHERE c.priority = :priority")
    , @NamedQuery(name = "CaseCategory.findByActive", query = "SELECT c FROM CaseCategory c WHERE c.active = :active")
    , @NamedQuery(name = "CaseCategory.findByCreatedAt", query = "SELECT c FROM CaseCategory c WHERE c.createdAt = :createdAt")
    , @NamedQuery(name = "CaseCategory.findByUpdatedAt", query = "SELECT c FROM CaseCategory c WHERE c.updatedAt = :updatedAt")})
public class CaseCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id")
    private String id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priority")
    private int priority;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "caseCategory", fetch = FetchType.LAZY)
    private Set<CaseTracker> caseTrackers;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

    public CaseCategory() {
    }

    public CaseCategory(String id) {
        this.id = id;
    }

    public CaseCategory(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @XmlTransient
    public Set<CaseTracker> getCaseTrackers() {
        return caseTrackers;
    }

    public void setCaseTrackers(Set<CaseTracker> caseTrackers) {
        this.caseTrackers = caseTrackers;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
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
        if (!(object instanceof CaseCategory)) {
            return false;
        }
        CaseCategory other = (CaseCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.drugsafety.entity.CaseCategory[ id=" + id + " ]";
    }
    
}

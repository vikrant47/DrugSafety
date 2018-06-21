/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.entity;

import com.example.drugsafety.entity.acl.User;
import com.example.drugsafety.model.task.TaskRecord;
import com.example.drugsafety.model.task.TaskStateRecord;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SS
 */
@Entity
@Table(name = "case_tracker")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaseTracker.findAll", query = "SELECT c FROM CaseTracker c")
    , @NamedQuery(name = "CaseTracker.findById", query = "SELECT c FROM CaseTracker c WHERE c.id = :id")
    , @NamedQuery(name = "CaseTracker.findByCsaeNumber", query = "SELECT c FROM CaseTracker c WHERE c.csaeNumber = :csaeNumber")
    , @NamedQuery(name = "CaseTracker.findByActive", query = "SELECT c FROM CaseTracker c WHERE c.active = :active")
    , @NamedQuery(name = "CaseTracker.findByCreatedAt", query = "SELECT c FROM CaseTracker c WHERE c.createdAt = :createdAt")
    , @NamedQuery(name = "CaseTracker.findByUpdatedAt", query = "SELECT c FROM CaseTracker c WHERE c.updatedAt = :updatedAt")
    , @NamedQuery(name = "CaseTracker.findByStatus", query = "SELECT c FROM CaseTracker c WHERE c.status = :status")
    , @NamedQuery(name = "CaseTracker.findByStartedAt", query = "SELECT c FROM CaseTracker c WHERE c.startedAt = :startedAt")
    , @NamedQuery(name = "CaseTracker.findByCaseCategory", query = "SELECT c FROM CaseTracker c WHERE c.caseCategory = :caseCategory")
    , @NamedQuery(name = "CaseTracker.findPendingCaseOrderByPriority", query = "SELECT c FROM CaseTracker c WHERE c.startedAt IS NULL ORDER BY c.priority DESC")
})
public class CaseTracker implements TaskRecord, Serializable {

    public static final int MIN_PRIORITY = 0;
    public static final int MAX_PRIORITY = 99999;
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    
    
    @Column(name = "id")
    private String id;

    
    @Column(name = "csae_number")
    private String csaeNumber;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    
    @Column(name = "status")
    private String status;

    @Column(name = "started_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedAt;

    @Column(name = "priority")
    private Integer priority;

    @JoinColumn(name = "case_category_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CaseCategory caseCategory;
    
   @JoinColumn(name = "task_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @JoinColumn(name = "updatedt_by", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedtBy;

    public CaseTracker() {
    }

    public CaseTracker(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCsaeNumber() {
        return csaeNumber;
    }

    public void setCsaeNumber(String csaeNumber) {
        this.csaeNumber = csaeNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        if (priority > CaseTracker.MAX_PRIORITY) {
            priority = CaseTracker.MAX_PRIORITY;
        } else if (priority < CaseTracker.MIN_PRIORITY) {
            priority = CaseTracker.MIN_PRIORITY;
        }
        this.priority = priority;
    }

    public CaseCategory getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(CaseCategory caseCategory) {
        this.caseCategory = caseCategory;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedtBy() {
        return updatedtBy;
    }

    public void setUpdatedtBy(User updatedtBy) {
        this.updatedtBy = updatedtBy;
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
        if (!(object instanceof CaseTracker)) {
            return false;
        }
        CaseTracker other = (CaseTracker) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "com.example.drugsafety.entity.CaseTracker[ id=" + id + " ]";
    }

    
    @Override
    public TaskStateRecord getTaskStateRecord() {
        return this.getCaseCategory();
    }

}

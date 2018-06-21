package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.example.drugsafety.entity.acl.Role;

import java.util.Date;

/**
 * The persistent class for the role_task_assignment_order database table.
 *
 */
@Entity
@Table(name = "task_state")
public class TaskState implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private int assignmentOrder;
    private Date createdAt;
    private String createdBy;
    private String entityType;
    private String recordId;
    private String taskType;
    private Date updatedAt;
    private String updatedBy;
    private Role role;

    public TaskState() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, length = 45)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "assignment_order")
    public int getAssignmentOrder() {
        return this.assignmentOrder;
    }

    public void setAssignmentOrder(int assignmentOrder) {
        this.assignmentOrder = assignmentOrder;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "created_by", length = 45)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "entity_type", nullable = false, length = 255)
    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Column(name = "record_id", nullable = false, length = 45)
    public String getRecordId() {
        return this.recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    @Column(name = "task_type", nullable = false, length = 45)
    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "updated_by", length = 45)
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    //bi-directional many-to-one association to Role
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

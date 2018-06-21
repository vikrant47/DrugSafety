package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.example.drugsafety.entity.acl.User;

import java.util.Date;

/**
 * The persistent class for the role_task_assignment_order database table.
 *
 */
@Entity
@Table(name = "role_task_assignment_order")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    private Date createdAt;
    private String createdBy;
    private String entityType;
    private String recordId;
    private String taskType;
    private Date updatedAt;
    private String updatedBy;
    private User assignedTo;
    private Date completedAt;
    private Date rejectedAt;
    private TaskState currentState;

    public Task() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completed_at")
    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rejected_at")
    public Date getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(Date rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    @ManyToOne
    @JoinColumn(name = "task_state_id", nullable = false)
    public TaskState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
    }

    
    public boolean isCompleted(Task task) {
        return this.getCompletedAt() != null;
    }
}

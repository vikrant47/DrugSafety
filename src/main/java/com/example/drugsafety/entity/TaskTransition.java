package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.example.drugsafety.entity.acl.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The persistent class for the role_task_assignment_order database table.
 *
 */
/**
 * @author SS
 *
 */
@Entity
@Table(name = "task_transition")
public class TaskTransition implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String id;

    private Date processedAt;
    private TaskState fromState;
    private TaskState toState;
    private User processedBy;
    private Task task;
    private String remarks;
    private String changesDone;

    public TaskTransition() {
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

    @ManyToOne
    @JoinColumn(name = "from_state", nullable = false)
    public TaskState getFromState() {
        return this.fromState;
    }

    public void setFromState(TaskState fromState) {
        this.fromState = fromState;
    }

    @ManyToOne
    @JoinColumn(name = "to_state", nullable = false)
    public TaskState getToState() {
        return this.toState;
    }

    public void setToState(TaskState toState) {
        this.toState = toState;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by", nullable = false)
    public User getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(User processedBy) {
        this.processedBy = processedBy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "changes_done")
    public HashMap<String, Object[]> getChangesDone() {
        try {
            return objectMapper.readValue(changesDone, new TypeReference<HashMap<String, Object[]>>() {
            });
        } catch (IOException ex) {
            Logger.getLogger(TaskTransition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setChangesDone(HashMap<String, Object[]> changesDone) {
        try {
            this.changesDone = objectMapper.writeValueAsString(changesDone);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(TaskTransition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.service;

import com.example.drugsafety.entity.TaskState; 
import com.example.drugsafety.util.BeanProcessor;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.drugsafety.model.task.TaskStateRecord;
import com.example.drugsafety.repository.TaskStateRepository;

/**
 *
 * @author SS
 */
@Service
public class TaskStateService {

    @Autowired
    EntityManager em;

    @Autowired
    TaskStateRepository taskStateRepository;

    @Autowired
    CrudService crudService;

    public TaskState newTaskTaskState(TaskState taskAssignmentOrder) {
        Object result = this.em.createQuery(
                "select t from TaskState where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType"
        )
                .setParameter("taskType", taskAssignmentOrder.getTaskType())
                .setParameter("recordId", taskAssignmentOrder.getRecordId())
                .setParameter("entityType", taskAssignmentOrder.getEntityType())
                .getFirstResult();
        if (result != null) {
            BeanProcessor.merge(result, taskAssignmentOrder);
            taskAssignmentOrder = (TaskState) result;
        }
        this.em.persist(taskAssignmentOrder);
        return taskAssignmentOrder;
    }

    public List<TaskStateRecord> getTaskStates(String taskType, TaskStateRecord roleOrderEntity) {
        return this.em.createQuery(
                "select t from TaskState "
                + "where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType "
                + "order by t.assignmentOrder"
        )
                .setParameter("taskType", taskType)
                .setParameter("entityType", roleOrderEntity.getClass().getName())
                .setParameter("recordId", roleOrderEntity.getId()).getResultList();
    }

    public TaskState getFirstTaskState(String taskType, TaskStateRecord roleOrderEntity) {
        Object result = this.em.createQuery(
                "select t from TaskState t "
                + " where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType "
                + " and t.assignmentOrder = (select MIN(inner.assignmentOrder) from TaskState inner where inner.taskType = :taskType and inner.recordId = :recordId and inner.entityType = :entityType )"
                + " order by t.assignmentOrder"
        )
                .setParameter("taskType", taskType)
                .setParameter("entityType", roleOrderEntity.getType().getName())
                .setParameter("recordId", roleOrderEntity.getId());
        if (result != null) {
            return (TaskState) result;
        }
        return null;
    }

    public TaskState getLastTaskState(String taskType, TaskStateRecord roleOrderEntity) {
        Object result = this.em.createQuery(
                "select t from TaskState t "
                + " where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType "
                + " and t.assignmentOrder = (select MAX(inner.assignmentOrder) from TaskState inner where inner.taskType = :taskType and inner.recordId = :recordId and inner.entityType = :entityType )"
                + " order by t.assignmentOrder"
        )
                .setParameter("taskType", taskType)
                .setParameter("entityType", roleOrderEntity.getType().getName())
                .setParameter("recordId", roleOrderEntity.getId()).getSingleResult();
        if (result != null) {
            return (TaskState) result;
        }
        return null;
    }

    public boolean isLastState(TaskState lastState) {
        Object result = this.em.createQuery(
                "select t from TaskState t "
                + " where t.id = :id "
                + " and t.assignmentOrder >= (select MAX(inner.assignmentOrder) from TaskState inner where inner.taskType = :taskType and inner.recordId = :recordId and inner.entityType = :entityType )"
                + " order by t.assignmentOrder"
        )
                .setParameter("id", lastState.getId())
                .setParameter("taskType", lastState.getTaskType())
                .setParameter("entityType", lastState.getEntityType())
                .setParameter("recordId", lastState.getAssignmentOrder()).getSingleResult();
        return result != null;
    }

    public TaskState getPrevTaskState(TaskState currentState) {
        Object result = this.em.createQuery(
                "select t from TaskState "
                + " where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType "
                + " and t.assignmentOrder < :currentSortOrder"
                + " order by t.assignmentOrder"
        )
                .setParameter("taskType", currentState.getTaskType())
                .setParameter("entityType", currentState.getEntityType())
                .setParameter("recordId", currentState.getRecordId())
                .setParameter("currentSortOrder", currentState.getAssignmentOrder()).getSingleResult();
        if (result != null) {
            return (TaskState) result;
        }
        return null;
    }

    public TaskState getNextTaskState(TaskState currentState) {
        Object result = this.em.createQuery(
                "select t from TaskState "
                + " where t.taskType = :taskType and t.recordId = :recordId and t.entityType = :entityType "
                + " and t.assignmentOrder > :currentSortOrder"
                + " order by t.assignmentOrder"
        )
                .setParameter("taskType", currentState.getTaskType())
                .setParameter("entityType", currentState.getEntityType())
                .setParameter("recordId", currentState.getRecordId())
                .setParameter("currentSortOrder", currentState.getAssignmentOrder()).getSingleResult();
        if (result != null) {
            return (TaskState) result;
        }
        return null;
    }
}

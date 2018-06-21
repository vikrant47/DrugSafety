/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.drugsafety.service.task;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.drugsafety.entity.Task;
import com.example.drugsafety.entity.TaskState;
import com.example.drugsafety.entity.TaskTransition;
import com.example.drugsafety.entity.acl.User;
import java.util.Date;
import javax.persistence.EntityManager;
import com.example.drugsafety.model.task.TaskRecord;
import com.example.drugsafety.service.CrudService;
import com.example.drugsafety.service.TaskStateService;
import com.example.drugsafety.util.BeanProcessor;
import com.sun.jmx.snmp.tasks.TaskServer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SS
 */
public class AbstractTaskService {

    private final EntityManager em;
    private final TaskStateService taskStateService;
    private final CrudService crudService;

    public AbstractTaskService(EntityManager em, TaskStateService taskStateService, CrudService crudService) {
        this.em = em;
        this.taskStateService = taskStateService;
        this.crudService = crudService;
    }

    public Task startNewTask(User assignedTo, String taskType, TaskRecord record) throws Exception {
        Task task = new Task();
        task.setAssignedTo(assignedTo);
        task.setRecordId(record.getId());
        task.setEntityType(record.getType().getName());
        task.setCreatedAt(new Date());
        TaskState firstState = taskStateService.getFirstTaskState(taskType, record.getTaskStateRecord());
        if (firstState == null) {
            throw new Exception("No task state configured for taskType " + taskType);
        }
        task.setCurrentState(firstState);
        this.em.persist(task);
        return task;
    }

    public TaskTransition Submit(Task task, TaskRecord taskRecord) throws Exception {
        TaskState nextState = taskStateService.getNextTaskState(task.getCurrentState());
        if (nextState == null) {
            throw new Exception("Invalid state configured for taskType " + task.getTaskType());
        }
        if (taskStateService.isLastState(nextState)) {
            task.setCompletedAt(new Date());
        }
        TaskTransition transition = new TaskTransition();
        transition.setTask(task);
        transition.setProcessedBy(task.getAssignedTo());
        transition.setFromState(task.getCurrentState());
        transition.setToState(nextState);
        task.setCurrentState(nextState);
        task.setAssignedTo(null);//for now it shouldn't be assigned to anybody
        transition.setChangesDone(this.crudService.diff(taskRecord, taskRecord.getId()));
        this.em.persist(transition);
        return transition;
    }

    public TaskRecord getTaskRecord(Task task) {
        try {
            return this.em.find((Class) Class.forName(task.getTaskType()), task.getRecordId());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AbstractTaskService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   
}

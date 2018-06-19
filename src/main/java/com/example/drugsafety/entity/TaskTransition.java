package com.example.drugsafety.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.example.drugsafety.entity.acl.Role;
import com.example.drugsafety.entity.acl.User;

import java.util.Date;

/**
 * The persistent class for the role_task_assignment_order database table.
 * 
 */
/**
 * @author SS
 *
 */
@Entity
@Table(name = "role_task_assignment_order") 
public class TaskTransition implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;

	private Date processedAt;
	private String fromState;
	private String toState;
	private User processedBy;
	private Task task;
	private String remarks;

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

	@Column(name = "from_state", nullable = false, length = 255)
	public String getFromState() {
		return this.fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	@Column(name = "to_state", nullable = false, length = 45)
	public String getToState() {
		return this.toState;
	}

	public void setToState(String toState) {
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

}
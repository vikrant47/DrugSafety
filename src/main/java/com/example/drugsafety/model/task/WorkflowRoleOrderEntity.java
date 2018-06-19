package com.example.drugsafety.model.task;

import java.util.Set;

import com.example.drugsafety.entity.acl.Role;

public interface WorkflowRoleOrderEntity<T> {
	public String getId();

	public String getType();

	public Set<T> getTasks();

	public Role getNextRole();

	public Role getPrevRole();
}

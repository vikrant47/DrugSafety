package com.example.drugsafety.model.task;

import java.util.Set;

import com.example.drugsafety.entity.acl.Role;

public interface TaskStateRecord {

    public String getId();

    public default Class getType() {
        return this.getClass();
    }
}

package com.example.drugsafety.model.task;

public interface TaskRecord {

    public String getId();

    public default Class getType() {
        return this.getClass();
    }

    public TaskStateRecord getTaskStateRecord();
}

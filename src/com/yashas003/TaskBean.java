package com.yashas003;

import java.util.Date;
import java.util.Objects;

public class TaskBean {

    private String taskName;
    private String desc;
    private String tags;
    private Date date;
    private int priority;

    public TaskBean() {
    }

    public TaskBean(String taskName, String desc, String tags, Date date, int priority) {
        this.taskName = taskName;
        this.desc = desc;
        this.tags = tags;
        this.date = date;
        this.priority = priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBean)) return false;
        TaskBean taskBean = (TaskBean) o;
        return getPriority() == taskBean.getPriority() &&
                getTaskName().equals(taskBean.getTaskName()) &&
                getDesc().equals(taskBean.getDesc()) &&
                getTags().equals(taskBean.getTags()) &&
                getDate().equals(taskBean.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaskName(), getDesc(), getTags(), getDate(), getPriority());
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "taskName='" + taskName + '\'' +
                ", desc='" + desc + '\'' +
                ", tags='" + tags + '\'' +
                ", date=" + date +
                ", priority=" + priority +
                '}';
    }
}

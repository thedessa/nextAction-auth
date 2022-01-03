package dev.ifrs.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NextAction {

  private String userId;
  private String taskId;
  private int type;
  private String title;
  private Boolean isCompleted;
  private String context;

  public NextAction() {

  }

  public NextAction(final String userId,
                    final String taskId,
                    final int type,
                    final String title,
                    final Boolean isCompleted) {
    this.userId = userId;
    this.taskId = taskId;
    this.type = type;
    this.title = title;
    this.isCompleted = isCompleted;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(final String taskId) {
    this.taskId = taskId;
  }

  public int getType() {
    return type;
  }

  public void setType(final int type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return isCompleted;
  }

  public void setCompleted(final Boolean completed) {
    isCompleted = completed;
  }

  public String getContext() {
    return context;
  }

  public void setContext(final String context) {
    this.context = context;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userId", userId)
        .append("taskId", taskId)
        .append("type", type)
        .append("title", title)
        .append("isCompleted", isCompleted)
        .append("context", context)
        .toString();
  }
}


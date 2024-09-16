package com.innominds.todo.entity;

import java.time.Instant;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("task")
@CompoundIndex(name = "idx_user_id_task_id", def = "{'userId':1, 'id':1}")
@CompoundIndex(name = "idx_user_id_last_modified_date", def = "{'userId':1, 'lastModifiedDate':-1}")
@CompoundIndex(name = "idx_notificationsent_target_date_lastModifiedDate", def = "{'notificationSent':1,'targetDate':1 ,'lastProcessedDate':1}")
public class Task extends BaseEntityMongoDB {

	private static final long serialVersionUID = 1L;
	private String title;
	private String description;
	private String userId;
	private String status;
	private Instant targetDate;
	private Instant lastProcessedDate;
	private boolean notificationSent;
	public static final String FIELD_STATUS = "status";
	public static final String FIELD_USER_ID = "userId";
	public static final String FIELD_NOTIFICATION_SENT = "notificationSent";
	public static final String FIELD_TARGET_DATE = "targetDate";
	public static final String FIELD_LAST_PROCESSED_DATE = "lastProcessedDate";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Instant targetDate) {
		this.targetDate = targetDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return new StringBuilder().append("id is:").append(getId()).append(" title is:").append(getTitle()).toString();
	}

	public boolean isNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}

	public Instant getLastProcessedDate() {
		return lastProcessedDate;
	}

	public void setLastProcessedDate(Instant lastProcessedDate) {
		this.lastProcessedDate = lastProcessedDate;
	}

}

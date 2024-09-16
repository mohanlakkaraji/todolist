package com.innominds.todo.dto;

import java.io.Serializable;
import java.util.List;

import com.innominds.todo.entity.Task;

public class TaskPaginationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private long totalCount;
	List<Task> tasks;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}

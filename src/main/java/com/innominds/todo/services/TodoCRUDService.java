package com.innominds.todo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innominds.todo.dao.TaskDAO;
import com.innominds.todo.entity.Task;
import com.innominds.todo.exception.TodoException;

@Service
public class TodoCRUDService {

	@Autowired
	TaskDAO taskDAO;

	public Task createTask(Task task) throws TodoException {
		return taskDAO.createTask(task);
	}

	public Task updateTask(Task task) throws TodoException {
		return taskDAO.updateTask(task);
	}

	public Task readTask(String id) throws TodoException {
		return taskDAO.readTask(id);
	}
	
	public void deleteTask(String id) throws TodoException {
		 taskDAO.deleteTask(id);
	}

	public Task updateStatus(String userId, String taskId, String status) throws TodoException {
		Map<String, Object> idFields = new HashMap<>();
		idFields.put(Task.FIELD_USER_ID, userId);
		idFields.put(Task.FIELD_ID, taskId);

		Map<String, Object> updateFields = new HashMap<>();
		updateFields.put(Task.FIELD_STATUS, status);

		return (Task) taskDAO.updateField(idFields, updateFields, Task.class);
	}
}

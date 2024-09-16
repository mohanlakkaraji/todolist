package com.innominds.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innominds.todo.dao.TaskDAO;
import com.innominds.todo.dto.TaskPaginationDTO;
import com.innominds.todo.entity.Task;
import com.innominds.todo.exception.TodoException;

@Service
public class TodoQueryService {

	@Autowired
	TaskDAO taskDAO;

	public TaskPaginationDTO listofTasks(String userId, int pageNumber, int limitPerPage) throws TodoException {
		return taskDAO.queryTasks(userId, pageNumber, limitPerPage);
	}

	public Task taskDetailsForUserIdAndTaskId(String userId, String taskId) throws TodoException {
		return taskDAO.queryTaskForUserIdAndTaskId(userId, taskId);
	}

}

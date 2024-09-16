package com.innominds.todo.dao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.innominds.todo.dto.TaskPaginationDTO;
import com.innominds.todo.entity.Task;
import com.innominds.todo.exception.LockException;
import com.innominds.todo.exception.TodoException;
import com.innominds.todo.repository.CRUDRepository;

@Service
public class TaskDAO {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	CRUDRepository repository;
	Logger logger = LoggerFactory.getLogger(TaskDAO.class);

	public Task createTask(Task task) throws TodoException {
		logger.info("in create Task with title is:" + task.getTitle());
		try {
			task.setCreationDate(Instant.now());
			task.setNotificationSent(false);
			return repository.insert(task);
		} catch (Exception e) {
			logger.error("RunTime exception occured in create Task ", e);
			throw new TodoException(TodoException.CREATED_FAILED_IN_DB,
					"Creation failed in DB for id:" + task.getId() + " ,version:" + task.getVersion());
		}
	}

	public Task readTask(String id) throws TodoException {
		logger.info("in create Task with id is:" + id);

		try {

			Optional<Task> task = repository.findById(id);
			if (task.isEmpty()) {
				logger.error("No record found for id : {}", id);
				return null;
			} else {
				return task.get();
			}

		} catch (Exception e) {
			logger.error("RunTime exception occured in readTask", e);
			throw new TodoException(TodoException.READ_FAILED_IN_DB, "read failed in DB for id:" + id);
		}
	}
	public void deleteTask(String id) throws TodoException {
		logger.info("in delete Task with id is:" + id);

		try {

			repository.deleteById(id);
			

		} catch (Exception e) {
			logger.error("RunTime exception occured in delete task", e);
			throw new TodoException(TodoException.DELETE_FAILED_IN_DB, "delete failed in DB for id:" + id);
		}
	}

	public Task updateTask(Task task) throws TodoException {
		logger.info("in update Task with title is:" + task.getTitle());
		task.setLastModifiedDate(Instant.now());
		try {
			return repository.save(task);
		} catch (OptimisticLockingFailureException lockException) {
			logger.error("Lock Exception occured for id {},version{}", task.getId(), task.getVersion());
			throw new LockException(LockException.RECORD_ALREADY_MODIFIED,
					"record already modified for id:" + task.getId() + ",version:" + task.getVersion());

		} catch (Exception e) {
			logger.error("RunTime exception occured", e);
			throw new TodoException(TodoException.UPDATE_FAILED_IN_DB,
					"Update failed in DB for id:" + task.getId() + " ,version:" + task.getVersion());
		}
	}

	public Object updateField(Map<String, Object> idFieldMap, Map<String, Object> updateFieldMap, @SuppressWarnings("rawtypes") Class updateClass)
			throws TodoException {

		try {
			Query query = new Query();
			Criteria andCriteria = new Criteria();
			List<Criteria> criteriaList = new ArrayList<>();
			idFieldMap.entrySet()
					.forEach(entry -> criteriaList.add(Criteria.where(entry.getKey()).is(entry.getValue())));
			andCriteria.andOperator(criteriaList);
			query.addCriteria(andCriteria);
			Update updateQuery = new Update();
			updateFieldMap.entrySet().forEach(entry -> updateQuery.set(entry.getKey(), entry.getValue()));
			updateQuery.inc("version", 1);
			FindAndModifyOptions option = FindAndModifyOptions.options().returnNew(true);

			@SuppressWarnings("unchecked")
			Object dbRes = mongoTemplate.findAndModify(query, updateQuery, option, updateClass);
			if (dbRes == null) {
				logger.error("No Records modified for field: {} with value {} " + idFieldMap);
				throw new TodoException(TodoException.UPDATE_FAILED_IN_DB, "Update failed in DB for id:" + idFieldMap);
			}
			return dbRes;
		}

		catch (Exception e) {
			logger.error("RunTime exception occured", e);
			throw new TodoException(TodoException.UPDATE_FAILED_IN_DB, "Update failed in DB for idKey:" + idFieldMap);
		}
	}

	public TaskPaginationDTO queryTasks(String userId, int pageNumber, int limitPerPage) {

		TaskPaginationDTO dto = new TaskPaginationDTO();
		Pageable pageable = PageRequest.of(pageNumber - 1, limitPerPage, Sort.Direction.DESC,
				Task.FIELD_LAST_MODIFIED_DATE);
		Page<Task> results = repository.listOfTasksForUserId(userId, pageable);

		dto.setTasks(results.getContent());
		dto.setTotalCount(results.getTotalElements());
		return dto;

	}

	public Task queryTaskForUserIdAndTaskId(String userId, String id) {

		return repository.taskForUserIdAndId(userId, id);

	}
}

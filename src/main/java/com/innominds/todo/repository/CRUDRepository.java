package com.innominds.todo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.innominds.todo.entity.Task;

@Repository
public interface CRUDRepository extends MongoRepository<Task, String> {

	@Query("{ 'userId' : {$eq: ?0 } }")
	Page<Task> listOfTasksForUserId(final String userId, final Pageable pageable);

	@Query("{$and :[{userId: ?0},{id: ?1}]}")
	Task taskForUserIdAndId(final String userId, String id);

}
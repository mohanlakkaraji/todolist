package com.innominds.todo.dto.scheduler.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.innominds.todo.email.services.EmailService;
import com.innominds.todo.entity.Task;

@Service
public class EmailNotificationSchedulerService {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	EmailService emailService;

	Logger logger = LoggerFactory.getLogger(EmailNotificationSchedulerService.class);

	@Scheduled(cron = "0 0 0 * * ?")
	public void sendEmailNotification() {

		Instant currentTime = Instant.now();
		currentTime = currentTime.truncatedTo(ChronoUnit.DAYS);
		logger.info("date criteria for the current time is: {}", currentTime);
		Criteria countCriteria = new Criteria();
		countCriteria.andOperator(Criteria.where(Task.FIELD_NOTIFICATION_SENT).is(false),
				Criteria.where(Task.FIELD_TARGET_DATE).lte(currentTime));

		Query countQuery = Query.query(countCriteria);
		int count = (int) mongoTemplate.count(countQuery, Task.class);
		int quotient = count / 4;
		if (count > 0 && quotient == 0) {
			quotient = count;
		}
		logger.info("No of records picked for processing is {}:", quotient);
		IntStream stream = IntStream.range(0, quotient);
		stream.parallel().forEach(value -> {
			Criteria criteria = new Criteria();
			criteria.andOperator(Criteria.where(Task.FIELD_NOTIFICATION_SENT).is(false));
			Query unProcessedNotificationQuery = Query.query(criteria);
			unProcessedNotificationQuery.with(Sort.by(Direction.ASC, Task.FIELD_LAST_PROCESSED_DATE));
			Update unProcessedNotificationUpdate = new Update();
			unProcessedNotificationUpdate.set(Task.FIELD_NOTIFICATION_SENT, true);
			unProcessedNotificationUpdate.currentDate(Task.FIELD_LAST_PROCESSED_DATE);
			unProcessedNotificationUpdate.inc(Task.FIELD_VERSION, 1);
			FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
			findAndModifyOptions.returnNew(true);
			Task task = mongoTemplate.findAndModify(unProcessedNotificationQuery, unProcessedNotificationUpdate,
					findAndModifyOptions, Task.class);
			if (!ObjectUtils.isEmpty(task)) {
				try {

					logger.info("start to send email notification for user id :{} and task id :{} ", task.getUserId(),
							task.getId());
					sendEmailNotification(task);
				} catch (Exception ae) {
					logger.error("Unable to process record {} in this cycle and hence marking as Not processed",
							task.getId(), ae);
					moveToPreviousState(task);
				}
			}
		});

	}

	private void sendEmailNotification(Task task) {
		// email id to be obtained for the given user id
		StringBuilder message = new StringBuilder("Dear ").append(task.getUserId()).append(",").append("\n")
				.append("The following task: ").append("\n").append(task.getDescription()).append("\n")
				.append(" is scheduled for completion by ").append(task.getTargetDate()).append("\n").append("Regards,")
				.append("\n").append("Notification Team");

		emailService.sendEmail("mlakkaraji@innominds.com", task.getTitle() + " Notification", message.toString());
	}

	/**
	 * This call back helps to move the record back to NOT PROCESSED state so that
	 * it will be picked by other instance and process it again
	 * 
	 * @param task
	 */
	private void moveToPreviousState(Task task) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("id").is(task.getId()));
		Query unProcessedNotificationQuery = Query.query(criteria);// .maxTime(mongoTimeoutPropertiesConfig.mongoQueryTimeout,TimeUnit.MILLISECONDS);
		Update unProcessedSamplesUpdate = new Update();
		unProcessedSamplesUpdate.set(Task.FIELD_NOTIFICATION_SENT, false);
		unProcessedSamplesUpdate.currentDate(Task.FIELD_LAST_PROCESSED_DATE);
		unProcessedSamplesUpdate.inc(Task.FIELD_VERSION, 1);
		mongoTemplate.findAndModify(unProcessedNotificationQuery, unProcessedSamplesUpdate, Task.class);
		logger.info("The status of the record {} changed from PROCESSED  back to NOT_PROCESSED", task.getId());

	}

}

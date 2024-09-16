package com.innominds.todo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innominds.todo.dto.scheduler.services.EmailNotificationSchedulerService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo Task scheduler Controller", description = "Todo Api for invoking scheduler")
@RestController
@RequestMapping("/todo/scheduler")
public class SchedulerController {

	@Autowired
	EmailNotificationSchedulerService schedulerService;

	@GetMapping("/start")
	public String invokeScheduler() {
		schedulerService.sendEmailNotification();
		return "success";
	}
}

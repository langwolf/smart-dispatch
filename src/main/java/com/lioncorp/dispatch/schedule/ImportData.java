package com.lioncorp.dispatch.schedule;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.lioncorp.dispatch.service.DataPublisher;

public class ImportData {
	private static final Logger logger = LoggerFactory
			.getLogger(ImportData.class);

	@Resource
	private DataPublisher dataPublisher;

		
	
	@Scheduled(cron = "0 */1 * * * ?") 
	public void importDataTest() throws Exception {
		logger.info("scheduled start");

		logger.info("scheduled end");
	}	
}

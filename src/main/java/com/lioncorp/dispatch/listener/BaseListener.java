package com.lioncorp.dispatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class BaseListener {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseListener.class);
	protected final Gson gson = new Gson();

		
	protected final static int REDIS = 0;
	protected final static int DB = 1;
	protected final static int KAFKA = 2;
	
	public final static int PAGE_SIZE = 10;

}
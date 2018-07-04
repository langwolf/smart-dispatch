package com.lioncorp.dispatch.queue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class KafkaServletListener implements ServletContextListener {

	private ClassPathXmlApplicationContext context = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != context) {
			context.destroy();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			context = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext-kafka.xml"});
		} catch (Exception e) {
			e.printStackTrace();
			if (null != context) {
				context.close();
			}
		}
	}

}

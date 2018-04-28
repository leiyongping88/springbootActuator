package com.gws.base.mvc.endpoint;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.ShutdownEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.gws.common.log.GwsLogger;
import com.gws.configuration.EnvironmentConfig;

/**
 *
 * @version
 * @author  2018年4月27日 下午7:54:09
 *
 */
@ConfigurationProperties(prefix = "endpoints.gwsshutdown")
public class GwsShutdownEndpoint extends AbstractEndpoint<Map<String, Object>> implements ApplicationContextAware {

	private static final Map<String, Object> NO_CONTEXT_MESSAGE = Collections
			.unmodifiableMap(Collections.<String, Object>singletonMap("message", "No Gws context to shutdown."));

	private static final Map<String, Object> SHUTDOWN_MESSAGE = Collections
			.unmodifiableMap(Collections.<String, Object>singletonMap("message", "Gws Context Shutting down, bye..."));

	private ConfigurableApplicationContext context;

	@Autowired
	private EnvironmentConfig envConfig;

	/**
	 * ； Create a new {@link ShutdownEndpoint} instance.
	 */
	public GwsShutdownEndpoint() {
		super("gwsshutdown", true, false);
	}

	@Override
	public Map<String, Object> invoke() {
		if (this.context == null) {
			return NO_CONTEXT_MESSAGE;
		}

		try {
			return SHUTDOWN_MESSAGE;
		} finally {

			// 是否延迟消费mq消息
			if (envConfig.isMqReconsumeLater() == false) {
				envConfig.setMqReconsumeLater(true);
			}

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
					GwsLogger.info("mq延迟消费消息result," + envConfig.isMqReconsumeLater());
					GwsShutdownEndpoint.this.context.close();
				}
			});
			thread.setContextClassLoader(getClass().getClassLoader());
			thread.start();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if (context instanceof ConfigurableApplicationContext) {
			this.context = (ConfigurableApplicationContext) context;
		}
	}
}

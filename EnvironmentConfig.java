package com.gws.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 配置文件
 *
 * @version
 * @author  2016年4月19日 下午9:22:32
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class EnvironmentConfig {
	
	/*******是否延迟消费mq消息*******/
	private boolean mqReconsumeLater = false;
}

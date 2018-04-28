package com.gws.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gws.base.mvc.CurrentUserMethodArgumentResolver;
import com.gws.base.mvc.endpoint.GwsShutdownEndpoint;
import com.gws.base.mvc.endpoint.GwsShutdownMvcEndpoint;
import com.gws.base.mvc.filter.AppSignOncePerFilter;
import com.gws.base.mvc.filter.H5OncePerFilter;
import com.gws.base.mvc.interceptor.BuyerOrderAuthInterceptor;
import com.gws.base.mvc.interceptor.GoodsAuthInterceptor;
import com.gws.base.mvc.interceptor.HttpInterceptor;
import com.gws.base.mvc.interceptor.LoginInterceptor;
import com.gws.base.mvc.interceptor.SecureAccessInterceptor;
import com.gws.base.mvc.interceptor.SellerOrderAuthInterceptor;
import com.gws.base.mvc.interceptor.UserOrderAuthInterceptor;
import com.gws.services.support.sms.SmsSendProcessorService;
import com.gws.services.support.sms.SmsVoiceSendProcessorService;

/**
 * spring web 配置
 *
 * @version
 * @author  2016年4月12日 下午1:30:40
 *
 */
@Configuration
public class GwsWebConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public GwsShutdownEndpoint gwsShutdownEndpoint() {
		return new GwsShutdownEndpoint();
	}

	@Bean
	@ConditionalOnBean(GwsShutdownEndpoint.class)
	@ConditionalOnEnabledEndpoint(value = "gwsshutdown", enabledByDefault = false)
	public GwsShutdownMvcEndpoint reloadMvcEndpoint(GwsShutdownEndpoint delegate) {
		return new GwsShutdownMvcEndpoint(delegate);
	}

}

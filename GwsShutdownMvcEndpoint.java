package com.gws.base.mvc.endpoint;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.mvc.ActuatorMediaTypes;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @version
 * @author  2018年4月27日 下午6:22:03
 *
 */
@ConfigurationProperties(prefix = "endpoints.gwsshutdown")
public class GwsShutdownMvcEndpoint extends EndpointMvcAdapter {

	public GwsShutdownMvcEndpoint(GwsShutdownEndpoint delegate) {
		super(delegate);
	}

	@PostMapping(produces = { ActuatorMediaTypes.APPLICATION_ACTUATOR_V1_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@Override
	public Object invoke() {
		if (!getDelegate().isEnabled()) {
			return new ResponseEntity<Map<String, String>>(
					Collections.singletonMap("message", "This endpoint is disabled"), HttpStatus.NOT_FOUND);
		}
		return super.invoke();
	}

}

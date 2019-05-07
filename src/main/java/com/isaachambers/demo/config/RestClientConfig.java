package com.isaachambers.demo.config;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.isaachambers.demo.utils.UserContextInterceptor;

@Configuration
public class RestClientConfig {

	/**
	 * The @LoadBalanced annotation indicates that this RestTemplate object is going
	 * to use Ribbon.
	 * 
	 * @return
	 */
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
		// Adding the UserContextInterceptor to the RestTemplate instance that has been
		// created
		if (interceptors == null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {

			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}
		return template;
	}

}

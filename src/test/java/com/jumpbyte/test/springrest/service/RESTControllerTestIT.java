/**
 * Copyright 2012 Daniel Sawano
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jumpbyte.test.springrest.service;

import com.jumpbyte.test.springrest.service.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Integration test for {@link RESTController}.
 * 
 * @author Daniel Sawano
 * 
 */
public class RESTControllerTestIT {
	Logger logger = LoggerFactory.getLogger(getClass());
	String baseUrl;

	@Before
	public void setUp() throws Exception {
		baseUrl = "http://localhost:9090/spring-examples-json-xml-ws/users";
	}

	@Test
	public void getUserWithJSON() {
		RestTemplate restTemplate = createJSONRestTemplate();
		User user = restTemplate.getForObject(baseUrl + "/123", User.class, (Object) null);

		assertNotNull(user);
		assertEquals(Long.valueOf(123), user.getId());
	}


	@Test
	public void getUserWithUnsupportedAccept() throws Exception {
		RestTemplate restTemplate = createStringRestTemplate();

		String user = restTemplate.getForObject(new URI(baseUrl + "/123"), String.class);

		logger.debug("Received data as: {}", user);
	}

	private RestTemplate createJSONRestTemplate() {
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new MappingJacksonHttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}

	private RestTemplate createStringRestTemplate() throws Exception {
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new StringHttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
}

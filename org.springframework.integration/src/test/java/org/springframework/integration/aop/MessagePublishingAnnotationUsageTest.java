/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.aop;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Oleg Zhurakousky
 * @since 2.0
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class MessagePublishingAnnotationUsageTest {
	@Autowired
	private TestBean testBean;
	@Autowired
	private QueueChannel channel;
	@Test
	public void demoMessagePublishingInterceptor(){
		String name = testBean.setName("John", "Doe");
		Assert.assertNotNull(name);
		Message<?> message = channel.receive();
		Assert.assertNotNull(message);
		Assert.assertEquals("John Doe", message.getPayload());
		Assert.assertEquals("123", message.getHeaders().get("bar"));
	}
	
	
	public static class TestBean{
		@Publisher(value="#return", channel="testChannel", headers="bar='123'")
		public String setName(String fname, String lname){
			return fname + " " + lname;
		}
	}
}

package com.emc.awg.scanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes=Application.class)
public class SmokeIT {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Test
	public void test() throws Exception {
		mockMvc = webAppContextSetup(context).build();
		
		MvcResult result = mockMvc.perform(get("/api/scan-service").param("location", "/Users/will/Documents/workspace-base/cna-scanner-jee"))
		.andExpect(status().isOk())
		.andReturn();
		
		String objectId = result.getResponse().getContentAsString();
		//now use it to retrieve
		
		result = mockMvc.perform(get("/api/report/{0}",objectId))
		.andExpect(status().isOk())
		.andReturn();
		//dump
		System.out.println(result.getResponse().getContentAsString());
	}

}

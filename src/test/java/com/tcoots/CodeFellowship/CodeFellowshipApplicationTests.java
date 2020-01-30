package com.tcoots.CodeFellowship;

import com.tcoots.CodeFellowship.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.security.RunAs;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class CodeFellowshipApplicationTests {

	@Autowired
	HomeController homeController;

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}





}

package com.lessard.codesamples.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:/integration_test.properties")
@SpringBootTest()
public class RestSpringBootApplicationTests {



	static {
		System.out.println("\n\n\nLOADING  UNIT TEST ENV \n\n\n");
	}


	@Test
	public void contextLoads() {

		System.out.println("\nUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU\n");

	}

}

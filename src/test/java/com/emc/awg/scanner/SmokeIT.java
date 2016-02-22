package com.emc.awg.scanner;

import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes=Application.class)
public class SmokeIT {

//	@Autowired
//	CommandLineRunner runner;
	
	@Test
	public void test() throws Exception {
		//setup a location
//		runner.run("/Users/will/Documents/workspace-base/cna-scanner-jee");
		Application.main(new String[]{"/Users/will/Documents/workspace-base/cna-scanner-jee"});
	}

}

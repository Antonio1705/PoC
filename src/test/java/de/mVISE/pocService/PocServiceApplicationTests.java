package de.mVISE.pocService;

import de.mVISE.pocService.service.NameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PocServiceApplicationTests {

	@Autowired
	NameService nameService;

	@Test
	void contextLoads() {

	}

}

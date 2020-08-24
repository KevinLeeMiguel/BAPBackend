package com.esteem.billingandpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.esteem.billingandpayment.domain.ServiceE;
import com.esteem.billingandpayment.repo.ServiceRepo;
import com.esteem.billingandpayment.service.ServiceService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(Lifecycle.PER_CLASS)
class BillingAndPaymentApplicationTests {
	@Autowired
	private ServiceService serviceEService;
	@Autowired
	private ServiceRepo serviceRepo;

	private String uuid = UUID.randomUUID().toString();

	@BeforeAll
	void SetUp(){
		ServiceE s = new ServiceE();
		s.setName("Service1");
		s.setDescription("description");
		s.setUuid(uuid);
		serviceRepo.save(s);
	}

	@Test
	void createNewService() {
		Map<String, String> req = new HashMap<>();
		req.put("name", "service2");
		req.put("description", "Description");
		ServiceE serv = serviceEService.create(req, "tester");
		assertEquals(req.get("name"), serv.getName());
	}

	@Test
	void findServiceByUuid(){
		System.out.println(uuid);
		Optional<ServiceE> s = serviceRepo.findByUuidAndDeletedStatus(uuid, false);
		assertTrue(s.isPresent());
		assertEquals("Service1", s.get().getName());
	}

}

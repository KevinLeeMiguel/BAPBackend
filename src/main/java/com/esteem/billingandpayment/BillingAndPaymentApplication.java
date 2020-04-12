package com.esteem.billingAndPayment;

import java.util.List;

import com.esteem.billingAndPayment.domain.Role;
import com.esteem.billingAndPayment.domain.SystemUser;
import com.esteem.billingAndPayment.domain.SystemUser_Role;
import com.esteem.billingAndPayment.repo.RoleRepo;
import com.esteem.billingAndPayment.repo.SystemUser_RoleRepo;
import com.esteem.billingAndPayment.repo.UserRepo;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillingAndPaymentApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private SystemUser_RoleRepo user_RoleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BillingAndPaymentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Role> roles = roleRepo.findByDeletedStatus(false);
		if (roles.isEmpty()) {
			Role r = new Role();
			r.setTitle("SUPERADMIN");
			roles.add(roleRepo.save(r));
		}
		Long users = userRepo.countByDeletedStatus(false);
		if (users <= 0) {
			SystemUser user = new SystemUser();
			user.setName("SuperAdmin");
			user.setEmailVerified(true);
			user.setDefaultPassword(false);
			user.setUsername("superadmin");
			user.setActive(true);
			user.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt()));
			SystemUser superadmin = userRepo.save(user);
			SystemUser_Role user_Role = new SystemUser_Role();
			user_Role.setRole(roles.get(0));
			user_Role.setUser(superadmin);
			user_RoleRepo.save(user_Role);
		}
	}

}

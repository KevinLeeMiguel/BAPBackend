package com.esteem.billingandpayment;

import java.util.List;

import com.esteem.billingandpayment.domain.Role;
import com.esteem.billingandpayment.domain.SystemUser;
import com.esteem.billingandpayment.domain.SystemUserRole;
import com.esteem.billingandpayment.repo.RoleRepo;
import com.esteem.billingandpayment.repo.SystemUserRoleRepo;
import com.esteem.billingandpayment.repo.UserRepo;

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
	private SystemUserRoleRepo userRoleRepo; 

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
			SystemUserRole userRole = new SystemUserRole();
			userRole.setRole(roles.get(0));
			userRole.setUser(superadmin);
			userRoleRepo.save(userRole);
		}
	}

}

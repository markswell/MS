package com.markswell.auth;

import com.markswell.auth.entity.Permission;
import com.markswell.auth.entity.User;
import com.markswell.auth.repository.PermissionRepository;
import com.markswell.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
						   BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, permissionRepository, passwordEncoder);
		};

	}

	@Transactional
	void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
				   BCryptPasswordEncoder passwordEncoder) {

		Permission permission = null;
		Permission findPermission = permissionRepository.findByDescription("Admin");
		if (findPermission == null) {
			permission = new Permission();
			permission.setDescription("Admin");
			permission = permissionRepository.saveAndFlush(permission);
		} else {
			permission = findPermission;
		}

		User admin = new User();
		admin.setUserName("markswell");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setPermissions(permissionRepository.findAll());

		User find = userRepository.findByUserName("markswell");
		if (find == null) {
			userRepository.save(admin);
		}
	}

}

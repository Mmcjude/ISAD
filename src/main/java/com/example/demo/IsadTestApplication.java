package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.MyAuthority;
import com.example.demo.model.MyUser;
import com.example.demo.repo.IMyAuthorityRepo;
import com.example.demo.repo.IMyUserRepo;

@SpringBootApplication
public class IsadTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsadTestApplication.class, args);
	}
	
	/**@Bean
	public CommandLineRunner testModel(IMyAuthorityRepo authRepo, 
			IMyUserRepo userRepo) {
		return new CommandLineRunner() {
			
				@Override
			public void run(String... args) throws Exception {
				PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
				
				MyAuthority a3 = new MyAuthority("GBLEADER");
				authRepo.save(a3);
				MyUser u3 = new MyUser("leader.gb", encoder.encode("GB_leader"), a3);
				a3.setUsers(Arrays.asList(u3));
				userRepo.save(u3);
				
				
				MyAuthority a1 = new MyAuthority("ADMIN");
				MyAuthority a2 = new MyAuthority("STAFF");
				authRepo.saveAll(Arrays.asList(a1, a2));
				
				MyUser u1 = new MyUser("admin", encoder.encode("Adm1n"), a1);
				MyUser u2 = new MyUser("staff", encoder.encode("staff.qwerty"), a2);
				
				a1.setUsers(Arrays.asList(u1));
				a2.setUsers(Arrays.asList(u2));
				userRepo.saveAll(Arrays.asList(u1, u2));

			}
		};
	}**/

}

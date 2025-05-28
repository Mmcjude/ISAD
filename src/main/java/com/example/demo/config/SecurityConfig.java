package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public UserDetailsManager createManager() {
		MyUserDetailsManagerService manager = new MyUserDetailsManagerService();
		return manager;
	}
	
	@Bean
	public DaoAuthenticationProvider createProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		provider.setPasswordEncoder(encoder);
		
		UserDetailsManager manager = createManager();
		provider.setUserDetailsService(manager);
		
		return provider;
		
	}
	
	@Bean
	public SecurityFilterChain configureEndpoints(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/gis/habitate/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/gis/habitate/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/weather-station/create").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/update/**").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/all").hasAnyAuthority("ADMIN")
				.requestMatchers("/weather-station/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/details/**").hasAuthority("ADMIN")
				
				.requestMatchers("/gis/municipality/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/gis/municipality/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/gis/local-cordinate-system/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/gis/local-cordinate-system/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/gis/geographic-information-system/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/gis/geographic-information-system/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/gis/country/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/gis/country/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")				
				
				.requestMatchers("/plant/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/plant/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/plant/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/plant/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/plant/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				
				.requestMatchers("/plant/variety/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/plant/variety/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/plant/variety/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/plant/variety/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/plant/variety/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				
				.requestMatchers("/unit-of-genetics-and-breeding/sample/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/species/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/species/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/species/all").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/species/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/species/details/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/create").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/update/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/all").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/delete/**").hasAnyAuthority("ADMIN", "GBLEADER", "STUFF")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/details/**").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
				
				.requestMatchers("/", "/home").hasAnyAuthority("ADMIN", "STUFF", "GBLEADER")
		        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
		        .anyRequest().authenticated()
				)
			    .formLogin(form -> form
			    		.loginPage("/login")
			    		.permitAll()
			    )
			    .logout(logout -> logout
			    		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			    	    .logoutSuccessUrl("/login?logout")
			    	    .permitAll()

			    );
			
			return http.build();
		}
		
	}


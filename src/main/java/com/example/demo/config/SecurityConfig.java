package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new MyUserDetailsManagerService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsManager userDetailsManager,
                                                            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
	
	@Bean
	public SecurityFilterChain configureEndpoints(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/gis/habitate/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/gis/habitate/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/habitate/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/weather-station/create").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/update/**").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/all").hasAnyAuthority("ADMIN")
				.requestMatchers("/weather-station/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/weather-station/details/**").hasAuthority("ADMIN")
				
				.requestMatchers("/gis/municipality/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/gis/municipality/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/municipality/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/gis/local-cordinate-system/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/gis/local-cordinate-system/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/local-cordinate-system/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/gis/geographic-information-system/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/gis/geographic-information-system/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/geographic-information-system/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/gis/country/create").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/update/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/gis/country/delete/**").hasAuthority("ADMIN")
				.requestMatchers("/gis/country/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")	
				
				.requestMatchers("/admin/user/create").hasAuthority("ADMIN")
				.requestMatchers("/admin/user/update/**").hasAuthority("ADMIN")
				.requestMatchers("/admin/user/all").hasAuthority("ADMIN")
				.requestMatchers("/admin/user/delete/**").hasAuthority("ADMIN")
				
				.requestMatchers("/plant/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/plant/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/plant/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/plant/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/plant/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				
				.requestMatchers("/plant/variety/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/plant/variety/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/plant/variety/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/plant/variety/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/plant/variety/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/sample/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sample/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/species/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/species/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/species/all").hasAnyAuthority("ADMIN", "GBLEADER", "STAFF")
				.requestMatchers("/unit-of-genetics-and-breeding/species/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/species/details/**").hasAnyAuthority("ADMIN", "GBLEADER", "STAFF")
				
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-fragment-length/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/reference-gene/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/sequencing/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sequencing/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sequencing/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sequencing/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/sequencing/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/unit-of-genetics-and-breeding/gene-expression/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-expression/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-expression/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-expression/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/unit-of-genetics-and-breeding/gene-expression/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/project/create").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/project/update/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/project/all").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				.requestMatchers("/project/delete/**").hasAnyAuthority("ADMIN", "GBLEADER")
				.requestMatchers("/project/details/**").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
				
				.requestMatchers("/", "/home").hasAnyAuthority("ADMIN", "STAFF", "GBLEADER")
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


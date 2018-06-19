package com.example.drugsafety.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.drugsafety.service.AuthProvider;
import com.example.drugsafety.service.UserProviderService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserProviderService userProviderService;
	
	@Autowired
	private AuthProvider authProvider;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return this.bCryptPasswordEncoder;
	}

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login","/configuration/ui","/configuration/security","/swagger-resources","/v2/api-docs","/resources/**", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**").permitAll()
				.and()
				.authorizeRequests()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password")
				.permitAll().and().logout()
				.permitAll().and().httpBasic().and().csrf().disable();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return new ProviderManager(Arrays.asList((AuthenticationProvider) authProvider));
	}
}
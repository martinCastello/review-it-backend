package ar.edu.unq.reviewitbackend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
	
	@Configuration
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {	
			//Heroku
			http.requiresChannel()
			  .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
			  .requiresSecure();
			
			http.authorizeRequests()
				.antMatchers("/").permitAll();
			http.csrf().disable()
			  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			  .and()
			  .authorizeRequests()
			  .antMatchers("/login").permitAll()
			  .antMatchers("/**").permitAll()
			  .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			  .antMatchers(HttpMethod.POST, "/**").permitAll()
			  .anyRequest().authenticated();
		
		}	
		
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    //Permission for frontend
		registry.addMapping("/**");
	}
  
  
  
  
}
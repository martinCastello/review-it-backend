package ar.edu.unq.reviewitbackend.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
	
	@Configuration
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		@Value("${auth0.audience}")
		private String audience;
			
		@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
		private String issuer;

		@Bean
		JwtDecoder jwtDecoder() {
			NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder)
              JwtDecoders.fromOidcIssuerLocation(issuer);

	      OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
	      OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
	      OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

	      jwtDecoder.setJwtValidator(withAudience);

	      return jwtDecoder;
		}  
	
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests() 
              .mvcMatchers("/users/signUp").permitAll()
              .mvcMatchers("/users**").permitAll()
              .anyRequest().authenticated()
              .and().cors()
              .and().oauth2ResourceServer().jwt();
		}
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    //Permission for frontend
		registry.addMapping("/**");
	}
  
  
  
  
}
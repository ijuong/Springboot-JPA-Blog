package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 빈등록
@EnableWebSecurity // 필터가 등록이 된다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig {

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf token 비활성화 (테스트 걸어주는게 좋음)
			.authorizeRequests()
		        	.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
		        	.permitAll()
		        	.anyRequest()
		        	.authenticated()
		        .and()
		        	.formLogin()
		        	.loginPage("/auth/loginForm")
		        	.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당주소로 오는 로그인을  가로챈서 대신로그인을 해준다.
		        	.defaultSuccessUrl("/")
		        	.failureUrl("/auth/joinForm")
		        	;
		return http.build();
	}
	
//	private PrincipalDetailService principalDetailService;
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		http
//			.authorizeHttpRequests()
//				.antMatchers("/auth/**")
//				.permitAll()
//				.anyRequest()
//				.authenticated()
//			.and()
//				.formLogin()
//				.loginPage("auth/loginForm");
//	}
	
//	@Bean //IoC
//	public BCryptPasswordEncoder encodePWD() {
//		return new BCryptPasswordEncoder();
//	}
	
	//시큐리티가 대신 로그인을 해줬는데 password 가로채기를 할때
	// 해당 password 가 어떤 해시로 암호화를 했는지 알아야 같은 해시로 db를 비교 할 수 있음 
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
//	}
	
}

package com.code.mypage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		/*시큐리티에 전부 걸리지 않도록 패스시킴*/
		web.ignoring().antMatchers("/**");
	}
}

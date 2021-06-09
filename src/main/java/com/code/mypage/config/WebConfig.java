package com.code.mypage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//web 관련 설정
@Configuration
public class WebConfig implements WebMvcConfigurer {


    /*
    패스워드 암호화
    BCryptPasswordEncoder 타입 메서드 등록
    @return
    */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}

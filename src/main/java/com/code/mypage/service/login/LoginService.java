package com.code.mypage.service.login;

import com.code.mypage.mapper.login.LoginMapper;
import com.code.mypage.vo.login.UserInfoVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    private BCryptPasswordEncoder passwordEncoder;
    private LoginMapper loginMapper;
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setLoginMapper(LoginMapper loginMapper){
        this.loginMapper = loginMapper;
    }

    public int isLogin(Map<String,Object> param) throws Exception{

        UserInfoVO userInfoVO = loginMapper.getUserInfo(param);
        int result = 0;
        if(userInfoVO !=null){
            //비교할때는 (암호화 안된 패스워드, 저장된(암호화된) 패스워드)
            boolean isMatched = passwordEncoder.matches(param.get("userPw").toString(), userInfoVO.getUserPw());

            if(isMatched){
                result =1; //비밀번호가 맞다면 1
            }else{
                result = -1; //비밀번호가 틀리면 -1
            }
        }else{
            result = -1; //아이디가 틀리면 -1
        }

        return  result;
    }

    public int insertUserInfo(UserInfoVO userInfoVO) throws Exception{
        int result = 0;

        String securedPass = passwordEncoder.encode(userInfoVO.getUserPw());
        userInfoVO.setUserPw(securedPass); // bcrypt를 이용하여 패스워드 암호화

        result = loginMapper.insertUserInfo(userInfoVO);
        return result;
    }
}

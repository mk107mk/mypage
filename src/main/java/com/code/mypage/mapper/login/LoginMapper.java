package com.code.mypage.mapper.login;

import com.code.mypage.vo.login.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface LoginMapper {

    //사용자 가져오기
    UserInfoVO getUserInfo(Map<String,Object> param) throws Exception;

    //사용자 등록
    int insertUserInfo(UserInfoVO userInfoVO) throws Exception;

}

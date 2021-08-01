package com.code.mypage.vo.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ParamVO {

    private String bbsTitle;
    private String bbsContents;
    private MultipartFile file;
}

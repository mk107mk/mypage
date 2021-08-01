package com.grid.gridcomp.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 페이징처리를 위한 객체
 */
@Component
@Data
public class PagingVO {
    private int countPerPage = 10;   	//한 페이지당 게시물 수. 기본 10개. 생성자 initVal 입력시 변경 됨.

    private int totalCount;				//전체 게시물 수
    private int currentPage;			    //현재 페이지.
    private int lastRow;				    //현 페이지에서 마지막 게시물. ex)2 페이지인 경우 20번째 게시물
    private int startRow;				    //현 페이지에서 시작 게시물. ex)2 페이지인 경우 11번째 게시물

    private int totalPage;				    //전체 페이지 수. ceil(전체 게시물 / 페이지당 수)
    private int pageRangeStart;			    //웹 페이지 하단 페이지 표시 부분. 시작번호. COUNT_PER_PAGE와 연동됨.
    private int pageRangeEnd;			    //웹 페이지 하단 페이지 표시 부분. 끝번호. COUNT_PER_PAGE와 연동됨.
    private int prevPage;
    private int nextPage;

    private int startRowNumCurrentPage; 	//리스트 페이지의 No(단순 증가 번호).

    public PagingVO() {
        countPerPage = 10;  // 한페이지에 보여줄 개수  기본 10개 지정
    }

    //생성자를 통해 한페이지에 보여줄 개수를 변경가능
    public PagingVO(int initVal) {
        countPerPage = initVal;
    }

    //setter 를 통해서 한페이지에 보여줄 개수를 지정가능
    public void setCountPerPage(String rowsPerPage) {
        if( StringUtils.isBlank(rowsPerPage) || rowsPerPage.equals("0") ) {
            countPerPage = 10;
        }else {
            countPerPage = Integer.parseInt( rowsPerPage );
        }
    }

    //게시물이 없어 0일 경우, 1로 기본값 세팅.
    public void setTotalCount(int totalCount) {
        if( totalCount < 1 ){
            this.totalCount = 1;
        }else{
            this.totalCount = totalCount;
        }
    }

    //현재 페이지 설정시,  현재 페이지의 시작 게시물 번호와 마지막 게시물 번호도 세팅
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        calculatePageRange(); // 페이지 범위 계산 메서드 실행
    }

    //현재 페이지 설정시,  현재 페이지의 시작 게시물 번호와 마지막 게시물 번호도 세팅
    public void calculatePageRange() {

        lastRow = currentPage * countPerPage;  // 마지막 페이지 row
        startRow = lastRow - countPerPage;  // 마지막 페이지 row 에서 한페이지에 보여줄 개수를 빼서, 시작 위치를 정한다.

        //시작 위치와 마지막 위치 보정 로직

        // 계산 실수로 시작위치가 0보다 작으면 그냥 0으로 맞춘다.
        if( startRow < 0 ) {
            startRow = 0;
        }

        //계산실수로 마지막 위치가 0보다 작으면 10으로 잡는다 즉 default 는 0 ~ 10 이 되겠다.
        if( lastRow <= 0 ) {
            lastRow = 10;
        }

        //계산실수로 마지막 위치가 전체 리스트개수보다 크면  마지막 위치와 전체리스트 카운트와 같게 한다.
        if( lastRow >= totalCount) {
            lastRow = totalCount;
        }

        //전체 게시물 수로 전체 페이지 계산.
        //ceil 은 올림을 위한 메서드. 1.1 일경우 1페이지 표시하고 1개 남으니까 실제 페이지는 1개를 위해 2페이지여야 한다.
        totalPage = (int)Math.ceil( (double)totalCount / (double)countPerPage );

        //현제 페이지로 웹 페이지 하단에 있는 페이지 범위 끝 계산.

        // 페이지를 표기할때  <이전> 1 2 3 4 <이후>  이렇게 할떄 1 2 3 4 그를 그려주기 위한 loop 의 마지막 값
        pageRangeEnd = (int)Math.ceil( (double)currentPage / (double)countPerPage ) * countPerPage;

        // 페이지를 표기할때  <이전> 1 2 3 4 <이후>  이렇게 할떄 1 2 3 4 그를 그려주기 위한 loop 의 시작 값
        pageRangeStart = pageRangeEnd - countPerPage + 1;

      //아까와 마찬가지로 보정
        if( pageRangeStart <= 0) {
            pageRangeStart = 1;
        }
        if( pageRangeEnd > totalPage ) {
            pageRangeEnd = totalPage;
        }
        if( pageRangeEnd == 0 ) {
            pageRangeEnd = 1;
        }

        // 보정 끝

        //현재 페이지가 1보다 크다면 이전페이지는 현재 페이지에서 하나 빼기
        if( currentPage > 1 ) {
            prevPage = currentPage - 1;
        }else {
            //아니라면 1페이지로 가기
            prevPage = 1;
        }

        //다음 페이지가 전체페이지 번호보다 작을 경우는 현재 페이지 +1 1
        if( nextPage < totalPage ) {
            nextPage = currentPage + 1;

            //더하기 한 값이 total 페이지보다 크거나 같으면 그냥 토탈페이지 값으로 치환
            if( nextPage >= totalPage ) {
                nextPage = totalPage;
            }
        }else {
            //아닐경우는 그냥 마지막페이지로 가자
            nextPage = totalPage;
        }


    }

    //html 에서 표현할 페이지를 그려준다.
    public String getPager() {

        StringBuilder sb = new StringBuilder();

        //처음으로 가기
        sb.append("<a class=\"btn_paging_first\" href=\"javascript:goPage('1')\"><span class=\"fas fa-angle-double-left\"><em>처음페이지</em></span> </a></a>\n");
        //이전페이지
        sb.append("<a class=\"btn_paging_prev\" href=\"javascript:goPage('"+ this.getPrevPage() +"')\"><span class=\"fas fa-angle-left\"><em>이전페이지</em></span></a>\n");

        //페이지 숫자 값 그리기 1 2 3 4 ...
        for(int i = this.getPageRangeStart() ; i <= this.getPageRangeEnd(); i++ ) {

            if(this.getCurrentPage() == i) {
                sb.append("<strong>"+i+"</strong>" + "\n");
            }else {
                sb.append("  <a href=\"javascript:goPage('"+i+"')\">" +i +"</a> \n");
            }

        }

        //다음페이지 그리기
        sb.append(" <a class=\"btn_paging_next\" href=\"javascript:goPage('"+this.getNextPage()+"')\"><span class=\"fas fa-angle-right\"><em>다음페이지</em></span></a> \n");
        //마지막페이지
        sb.append(" <a class=\"btn_paging_end\" href=\"javascript:goPage('"+this.getNextPage()+"')\"><span class=\"fas fa-angle-double-right\"><em>다음페이지</em></span></a> \n");

        return sb.toString();
    }
}

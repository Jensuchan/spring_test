package com.ezen.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingVO {
	
	private int pageNo; //현재 페이지 번호
	private int qty; //한 화면에 보여줄 게시글 수(10개)
	
	private String type; //검색에 사용될 멤버변수 추가
	private String keyword; //검색어
	
	public PagingVO() {
		this.pageNo = 1;
		this.qty = 10;
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	public int getPageStart() {
		//DB상에서 limit의 시작값 구하는 메서드
		return (this.pageNo - 1) * this.qty;
	}
	
	//여러가지의 타입을 같이 검색하기 위해서 타입ㅇ들 배열로 구분
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}

}

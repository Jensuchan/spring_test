package com.ezen.www.service;

import javax.servlet.http.HttpServletRequest;

import com.ezen.www.domain.MemberVO;

public interface MemberService {

	int signUp(MemberVO mvo);

	MemberVO isUser(MemberVO mvo);

	void lastLogin(String id);

	int modify(MemberVO mvo);

	int delete(String id);

}

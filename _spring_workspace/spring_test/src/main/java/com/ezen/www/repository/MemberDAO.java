package com.ezen.www.repository;

import com.ezen.www.domain.MemberVO;

public interface MemberDAO {

	MemberVO getUser(String id);

	int insert(MemberVO mvo);

	void lastLogin(String id);

	int update(MemberVO mvo);

	int delete(String id);

}

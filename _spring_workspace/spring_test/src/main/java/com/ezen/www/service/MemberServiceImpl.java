package com.ezen.www.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Inject
	private MemberDAO mdao;
	
	@Inject
	BCryptPasswordEncoder passwordEncoder;
	
	@Inject
	HttpServletRequest request;

	@Override
	public int signUp(MemberVO mvo) {
		//아이디가 중복되면 회원가입 실패
		// => 아이디만 주고 DB에서 일치하는 mvo 객체를 리턴
		// 일치하는 유저가 있다면 가입 실패, 없다면 가입 가능
		MemberVO tempMvo = mdao.getUser(mvo.getId());
		if(tempMvo != null) {
			//기존 아이디가 있는 경우
			return 0;
		}
		//아이디가 중복되지 않는다면 회원가입 진행
		//password가 null이거나 값이 없다면 가입불가.
		if(mvo.getId() == null || mvo.getId().length() == 0) {
			return 0;
		}
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			return 0;
		}
		//회원가입 진행
		//password는 암호화 하여 가입
		//암호화(encode) / matches(입력된비번, 암호화된비번) => true / false
		String pw = mvo.getPw();
		
		String encodePw = passwordEncoder.encode(pw); //패스워드 암호화
		mvo.setPw(encodePw);
		
		//회원가입
		int isOk = mdao.insert(mvo);
		return isOk;
	}

	@Override
	public MemberVO isUser(MemberVO mvo) {
		log.info("member login service");
		//로그인 유저 확인
		//아이디를 주고 해당 아이디의 객체를 리턴
		MemberVO tempMvo = mdao.getUser(mvo.getId()); //회원가입할 때 했던 메서드 호출
		
		//해당 아이디가 없는 경우
		if(tempMvo == null) {
			return null;
		}
		
		//matches(원래비번, 암호화된 비번) 비교
		if(passwordEncoder.matches(mvo.getPw(), tempMvo.getPw())) {
			return tempMvo;
		}
		return null;
	}

	@Override
	public void lastLogin(String id) {
		log.info("last_login service");
		mdao.lastLogin(id);
	}

	@Override
	public int modify(MemberVO mvo) { //pw = ""
		log.info("modify service");
//		//비밀번호 변경안했을때
//		if(mvo.getPw() == "") {
//			MemberVO tempMvo = mdao.getUser(mvo.getId());
//			mvo.setPw(tempMvo.getPw());
//			return mdao.update(mvo);			
//		}
//		String newPw = passwordEncoder.encode(mvo.getPw());
//		mvo.setPw(newPw);
		
		//pw의 여부에 따라서 변경사항을 나누어 처리
		//pw가 없다면 기존값으로 설정 / pw가 있다면 암호화 처리하여 수정
		if(mvo.getPw() == null || mvo.getPw().length() == 0) {
			//비밀번호가 비어있다면
			MemberVO sesMvo = (MemberVO)request.getSession().getAttribute("ses");
			mvo.setPw(sesMvo.getPw());
		}else {
			String setpw = passwordEncoder.encode(mvo.getPw());
			mvo.setPw(setpw);
		}
		log.info(">>> pw 수정후 mvo >> {} ", mvo);
		return mdao.update(mvo);
	}

	@Override
	public int delete(String id) {
		log.info("delete service");
		return mdao.delete(id);
	}

}

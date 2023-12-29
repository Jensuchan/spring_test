package com.ezen.www.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.MemberVO;
import com.ezen.www.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	@Inject
	private MemberService msv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>> mvo >> {} ", mvo);
		int isOk = msv.signUp(mvo);
		log.info(">>> signUp ? "+(isOk > 0? "OK" : "Fail"));
		return "index";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, Model m, HttpServletRequest request) {
		log.info(">>> mvo >> {} ", mvo);
		//mvo 객체가 DB에 일치하는지 체크
		MemberVO loginMvo = msv.isUser(mvo);
		if(loginMvo != null) {
			HttpSession ses = request.getSession();
			ses.setAttribute("ses", loginMvo);
			ses.setMaxInactiveInterval(60*10);
		}else {
			m.addAttribute("msg_login", "1"); //실패했을때임
		}
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, Model m) {
		//세션객체를 삭제하고 세션 끊기
		MemberVO mvo = (MemberVO)request.getSession().getAttribute("ses");
		msv.lastLogin(mvo.getId());
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		m.addAttribute("msg_logout", "1");
		return "index";
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, RedirectAttributes re) {
		log.info(">>> mvo >> {} ", mvo);
		int isOk = msv.modify(mvo);
		log.info(">>> modify ? >> "+(isOk > 0? "OK" : "Fail"));
		re.addFlashAttribute("msg_modify", isOk);
		return "redirect:/member/logout";
	}
	
	@GetMapping("/delete")
	public String delete(HttpServletRequest request, Model m) {
		String id = ((MemberVO)request.getSession().getAttribute("ses")).getId();
		log.info(">>> id >> {} "+id);
		int isOk = msv.delete(id);
		request.getSession().removeAttribute("ses");
		request.getSession().invalidate();
		log.info(">>> delete ? "+(isOk > 0? "OK" : "Fail"));
		m.addAttribute("msg_delete", isOk);
		return "index";
	}
	
	
}

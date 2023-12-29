package com.ezen.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.handler.FileHandler;
import com.ezen.www.handler.PagingHandler;
import com.ezen.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
	
//	@Autowired 비슷한 기능
	@Inject
	private BoardService bsv;
	
	@Inject
	private FileHandler fhd;
	
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	
	//경로와 리턴의 값이 같을 경우 생략 가능
	// /board/register => /board/register
	@GetMapping("/register")
	public void register() {}
	
	//@RequestParam("name")String name : 파라미터 받을 때
	//required : 필수여부 (false) : 파라미터가 없어도 예외가 발생하지 않음.
	@PostMapping("/register")
	public String register(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
		log.info(">>> bvo >> {} ", bvo);
		log.info(">>> files >> {} ", files.toString());
		//파일핸들러 처리.
		List<FileVO> flist = null;
		
		//파일이 있을 경우만 fhd를 호출
		if(files[0].getSize() > 0) {
			flist = fhd.uploadFiles(files); 
			log.info(">>> flist >> {} ", flist);
			//파일 등록 전에 파일 개수를 bvo fileCount에 직접 set
			bvo.setFileCount(flist.size());
		}else {
			log.info("file null");
		}
		BoardDTO bdto = new BoardDTO(bvo, flist);
		
		//보드와 파일이 등록이 완료되는 시점
		int isOk = bsv.register(bdto);
		
		
		
		log.info(">>> board register >>> {} "+(isOk > 0 ? "OK" : "Fail"));
		
		
		
		//int isOk = bsv.register(bvo);
		//목적지 경로
		return "redirect:/board/list";
	}
	
	// /board/list => /board/list 이기 때문에 void로 처리해도 상관없음.
	@GetMapping("/list")
	public String list(Model m, PagingVO pgvo) {
		log.info(">>> pgvo >> {} ", pgvo); //pagvo, qty, type, keyword
		//리턴타입은 목적지 경로에 대한 타입(destPage가 리턴이라고 생각)
		//Model 객체 => setAttribute 역할을 하는 객체
//		int commentCount = bsv.getCommentCount(pgvo);
//		int fileCount = bsv.getFileCount(pgvo);
		
		m.addAttribute("list", bsv.getList(pgvo));
		
		//페이징 핸들러 객체 다시 생성(pgvo, totalCount)
		int totalCount = bsv.getTotalCount(pgvo);
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		m.addAttribute("ph", ph);
		return "/board/list";
	}
	
	@GetMapping({"/detail", "/modify"})  //두개를 쓰려면 중괄호로 묶어줘야한다
	public void detail(Model m, @RequestParam("bno") int bno) {
		log.info(">>> bno >> "+bno);
		//파일 내용도 포함해서 같이 보내기
		m.addAttribute("boardDTO", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
		log.info(">>> bvo >> {} ", bvo);
		//update
		List<FileVO> flist = null;
		if(files[0].getSize() > 0) {
			flist = fhd.uploadFiles(files);
		}
		BoardDTO boardDTO = new BoardDTO(bvo, flist);
		//update
		bsv.update(boardDTO);
		
//		m.addAttribute("bno", bvo.getBno());
		return "redirect:/board/detail?bno="+bvo.getBno(); //bno가 필요
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam("bno") int bno, RedirectAttributes re) {
		log.info(">>> bno >> {} "+ bno);
		int isOk = bsv.remove(bno);
		//페이지가 새로고침 될 때 남아있을 필요가 없는 데이터 
		//리다이렉트 될 때 데이터를 보내는 객체(RedirectAttribute)
		//한번만 일회성으로 데이터 보낼 때 사용
		re.addFlashAttribute("isDel", isOk);
		return "redirect:/board/list";
	}
	
	@DeleteMapping(value="/file/{uuid}", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("uuid") String uuid){
		log.info(">>> uuid >> {} ", uuid);
		int isOk = bsv.removeFile(uuid);
		return (isOk > 0)? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}

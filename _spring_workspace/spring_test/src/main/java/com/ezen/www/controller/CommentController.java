package com.ezen.www.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.service.CommentService;

import lombok.extern.slf4j.Slf4j;


@RestController //비동기 컨트롤러
@Slf4j
@RequestMapping("/comment/*")
public class CommentController {
	
	@Inject
	private CommentService csv;
	
	// ResponseEntity 객체 사용 : body내용 + httpStatus 상태
	// @RequestBody : body값 추출
	// consumes : 가져오는 데이터의 형태
	// produces : 보내는 데이터의 형식 / 나가는 데이터 타입 : MediaType
	// json : application/json / text : text_plain
	// INTERVAL_SERVER_ERROR : 500번대 에러 (서버에러)
	
	
	@PostMapping(value="/post", consumes="application/json", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		log.info(">>> cvo >> {} ", cvo);
		int isOk = csv.post(cvo);
		return (isOk > 0)? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value="/{bno}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentVO>> list(@PathVariable("bno") int bno){
		log.info(">>> bno >> {} "+ bno);
		
		List<CommentVO> list = csv.list(bno);
		return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
	}
	
	
	@DeleteMapping(value="/{cnoVal}", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> delete(@PathVariable("cnoVal") int cno){
		log.info(">>> cno >> {} "+cno);
		int isOk = csv.remove(cno);
		return (isOk > 0)? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//컨슘 리퀘스트바디 > 어떤 미디어타입 produces > 나가는거
	
	@PutMapping(value="/modify", consumes="application/json", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> update(@RequestBody CommentVO cvo){
		log.info(">>> cvo >> {} ", cvo);
		int isOk = csv.update(cvo);
		return (isOk > 0)? new ResponseEntity<String>("1", HttpStatus.OK):
			new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}

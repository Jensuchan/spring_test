package com.ezen.www.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardTest {
	
	@Inject
	private BoardDAO bdao;
	
	@Test
	public void insertBoard() {
		log.info(">>> Test Insert in >>>");
		
		for(int i=0; i<300; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test Title"+i);
			bvo.setWriter("Tester");
			bvo.setContent("Test Content"+i);
			bdao.insert(bvo);
		}
	}
}

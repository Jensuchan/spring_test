package com.ezen.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.CommentVO;
import com.ezen.www.repository.CommentDAO;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
	
	@Inject
	private CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("comment service OK");
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> list(int bno) {
		return cdao.getList(bno);
	}

	@Override
	public int remove(int cno) {
		return cdao.delete(cno);
	}

	@Override
	public int update(CommentVO cvo) {
		return cdao.update(cvo);
	}
}

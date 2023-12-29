package com.ezen.www.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ezen.www.domain.BoardDTO;
import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.FileVO;
import com.ezen.www.domain.PagingVO;
import com.ezen.www.repository.BoardDAO;
import com.ezen.www.repository.CommentDAO;
import com.ezen.www.repository.FileDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
	
	@Inject
	private BoardDAO bdao;
	
	@Inject
	private CommentDAO cdao;
	
	@Inject
	private FileDAO fdao;

	@Override
	public int register(BoardDTO bdto) {
		log.info("register service impl");
		//기존 보드 내용을 DB에 저장
		int isOk = bdao.insert(bdto.getBvo());
		
		//flist를 db에 저장
		if(bdto.getFlist() == null) {
			//파일의 값이 없다면...
			isOk *= 1 ;
		}else {
			//파일 저장
			if(isOk > 0 && bdto.getFlist().size()>0) {
				//fvo는 bno는 아직 설정되기 전
				//현재 bdto 시점에서는 아직 bno가 생성되지 않음
				//insert를 통해 자동생성 => DB에서 검색해서 가져오기
				int bno = bdao.selectBno();
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
		
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("list service impl");
		int isOk = bdao.updateCommentCount();
		if(isOk == 0) {
			log.info("updateCommentCount error");
		}
//		bdao.updateFileCount();
		
		
		return bdao.selectList(pgvo);
	}
	
//		List<BoardVO> boardVO = bdao.selectList(pgvo);
//		for(BoardVO bvo : boardVO) {
//			int bno = bvo.getBno();
//			int commentCount = cdao.getCommentCount(bno);
//			bdao.updateCmtCount(bno, commentCount);
//			int fileCount = fdao.getFileCount(bno);
//			bdao.updateFileCount(bno, fileCount);
//			bvo.setCommentCount(commentCount);
//			bvo.setFileCount(fileCount);
////			bdao.updateAll(bno, commentCount, fileCount);
//		}
	

	@Override
	public BoardDTO getDetail(int bno) {
		//readCount 증가
		bdao.updateReadCount(bno);
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBvo(bdao.selectOne(bno)); //게시글 내용 채우기
		boardDTO.setFlist(fdao.getFileList(bno)); //bno에 해당하는 모든 파일 리스트 검색
		return boardDTO;
	}


	@Override
	public void update(BoardDTO bdto) {
		log.info("modify service impl");
		
		int isOk = bdao.update(bdto.getBvo()); // 보드내용 수정
		if(bdto.getFlist() == null) {
			isOk *= 1; //이미 처리된 것과 같은 효과.
		}else {
			if(isOk > 0 && bdto.getFlist().size() > 0) {
				int bno = bdto.getBvo().getBno();
				for(FileVO fvo : bdto.getFlist()) {
					fvo.setBno(bno);
					isOk *= fdao.insertFile(fvo);
				}
			}
		}
	}

	@Override
	public int remove(int bno) {
		log.info("remove service impl");
		return bdao.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public int removeFile(String uuid) {
		return fdao.removeFile(uuid);
	}
	
}

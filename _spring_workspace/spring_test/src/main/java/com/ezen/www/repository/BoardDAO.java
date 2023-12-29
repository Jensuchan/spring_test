package com.ezen.www.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ezen.www.domain.BoardVO;
import com.ezen.www.domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList(PagingVO pgvo);

	void updateReadCount(int bno);

	BoardVO selectOne(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	int getTotalCount(PagingVO pgvo);

	int selectBno();

//	void updateCmtCount(@Param("bno")int bno, @Param("commentCount")int commentCount);

//	void updateFileCount(@Param("bno")int bno, @Param("fileCount")int fileCount);

	int updateCommentCount();

//	void updateFileCount();

}

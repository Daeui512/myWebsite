package edu.web.board.persistence;

import java.util.List;

import edu.web.board.domain.BoardVO;

public interface BoardDAO {
	public abstract int insert(BoardVO vo);
	
	public abstract List<BoardVO> select();
	
	public abstract BoardVO select(int bno);
	
	public abstract int update(BoardVO vo);
	
	public abstract int delete(int bno);
	
}

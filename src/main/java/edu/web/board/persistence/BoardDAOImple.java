package edu.web.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.web.board.domain.BoardVO;
import edu.web.board.util.PageCriteria;
import edu.web.dbcp.connmgr.ConnMgr;

public class BoardDAOImple implements BoardDAO,BoardQuery{
	
	private static BoardDAOImple instance = null;
	
	private BoardDAOImple() {}
	
	public static BoardDAOImple getInstance() {
		if(instance == null) {
			instance = new BoardDAOImple();
		}
		return instance;
	}
	
	
	@Override
	public int insert(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		return result;
	}

	@Override
	public List<BoardVO> select() {
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO vo = null;
		int bno;
		String title, content, userid, cdate;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				bno = rs.getInt(COL_BNO);
				title = rs.getString(COL_TITLE);
				content = rs.getString(COL_CONTENT);
				userid = rs.getString(COL_USERID);
				cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		return list;
	}

	@Override
	public BoardVO select(int bno) {
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_BY_BNO);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bno = rs.getInt(COL_BNO);
				String title = rs.getString(COL_TITLE);
				String content = rs.getString(COL_CONTENT);
				String userid = rs.getString(COL_USERID);
				String cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		return vo;
	}

	@Override
	public int update(BoardVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getBno());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		return result;
	}

	@Override
	public int delete(int bno) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setInt(1, bno);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		
		return result;
	}

	@Override
	public List<BoardVO> select(PageCriteria c) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO vo = null;
		int bno;
		String title, content, userid, cdate;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_PAGESCOPE);
			// 1 ~ 5, 6 ~ 10, 11 ~ 15
			pstmt.setInt(1, c.getStart());
			pstmt.setInt(2, c.getEnd());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bno = rs.getInt(COL_BNO);
				title = rs.getString(COL_TITLE);
				content = rs.getString(COL_CONTENT);
				userid = rs.getString(COL_USERID);
				cdate = rs.getString(COL_CDATE);
				vo = new BoardVO(bno, title, content, userid, cdate);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		return list;
	}

	@Override
	public int getTotatlNums() {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_TOTAL_CNT);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt("TOTAL_CNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		return cnt;
	}
}

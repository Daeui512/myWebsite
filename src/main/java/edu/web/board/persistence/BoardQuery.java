package edu.web.board.persistence;

public interface BoardQuery {
	public static final String TABLE_NAME = "BOARD";
	public static final String COL_BNO = "BNO";
	public static final String COL_TITLE = "TITLE";
	public static final String COL_CONTENT = "CONTENT";
	public static final String COL_USERID = "USERID";
	public static final String COL_CDATE = "CDATE";
	
	//새 글 작성
	// insert into board values
	public static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + 
			" VALUES (BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS'))";
	
	//전체 게시글 가져오기
	//SELECT * FROM BOARD ORDER BY BNO DESC;
	public static final String SQL_SELECT = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_BNO + " DESC";

	//선택 게시글 하나 가져오기
	//SELECT * FROM BOARD WHERE BNO = ?
	public static final String SQL_SELECT_BY_BNO = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_BNO + " = ?";
	
	//게시글 수정
	//UPDATE BOARD SET TITLE=?, CONTENT=?, CDATE=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS') WHERE BON = ?
	public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " 
											+ COL_TITLE + " = ?, "
											+ COL_CONTENT + " = ?, "
											+ COL_CDATE + " = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS') WHERE "
											+ COL_BNO + " = ?";
	
	//게시글 삭제
	//DELETE FROM BOARD WHERE BNO = ?
	public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_BNO + " = ?";
	
	//게시글 페이징
	//SELECT B.BNO, B.TITLE, B.CONTENT, B.USERID, B.CDATE FROM (
    //SELECT ROWNUM AS RN, A.* FROM (
    //        SELECT * FROM BOARD ORDER BY BNO DESC
    //    )A
    // )B WHERE RN BETWEEN ? AND ?;
	public static final String SQL_SELECT_PAGESCOPE = 
			"SELECT B." + COL_BNO + ", B." + COL_TITLE +", B." + COL_CONTENT + ", B." + COL_USERID + ", B." + COL_CDATE 
			+ " FROM ( SELECT ROWNUM AS RN, A.* FROM ( SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_BNO + " DESC "
			+ ")A" + ")B WHERE RN BETWEEN ? AND ?";
	// SELECT COUNT(*) TOTAL_CNT FROM BOARD;
	public static final String SQL_TOTAL_CNT = "SELECT COUNT(*) TOTAL_CNT FROM " + TABLE_NAME;
}
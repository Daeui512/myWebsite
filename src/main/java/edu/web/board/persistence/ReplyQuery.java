package edu.web.board.persistence;

public interface ReplyQuery {
	public static final String TABLE_NAME = "REPLY";
	public static final String COL_REPLY_NO = "REPLYNO";
	public static final String COL_REPLY_BNO = "REPLYBNO";
	public static final String COL_REPLY_CONTENT = "REPLYCONTENT";
	public static final String COL_REPLY_USERID = "REPLYID";
	public static final String COL_REPLY_DATE = "REPLYDATE";

	// INSERT INTO REPLY
	// VALUES (REPLY_SEQ.NEXTVAL,?,?,?,TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS');
	public static final String SQL_INSERT = 
			"INSERT INTO " + TABLE_NAME + " VALUES "
			+ "(BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS'))";
	
	// SELECT * FROM REPLY WHERE REPLY_BNO = ? ORDER BY REPLYNO DESC;
	public static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_REPLY_BNO 
			+ " = ? ORDER BY " + COL_REPLY_NO + " DESC";
	
	// UPDATE REPLY SET
	// REPLYCONTENT=?, REPLYDATE=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS') WHERE REPLYNO = ? AND REPLYBNO = ?
	public static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " 
			+ COL_REPLY_CONTENT + " = ?, " + COL_REPLY_DATE + "=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:MI:SS')"
			+ " WHERE " + COL_REPLY_NO + " = ? AND " + COL_REPLY_BNO + " = ?";
	
	// DELETE FROM REPLY WHERE REPLYNO = ? AND REPLYBNO = ?
	public static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE "
			+ COL_REPLY_NO + " = ? AND " + COL_REPLY_BNO + " = ?";
}

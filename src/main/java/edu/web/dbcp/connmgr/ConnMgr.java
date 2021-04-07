package edu.web.dbcp.connmgr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// DBCP(Data Base ConnectionPool)
// - 브라우저에서 서버에 호출 시 매번 DB연결 객체를 생성하면 과부하가 발생할 수 있음
// - 이를 해결하기 위해 다수의 연결을 컨트롤하는 기법을 DBCP라고 함
// - DBCP 원리
//	1) 웹 컨테이너가 실행되면서 커넥션(Connection) 객체를 미리 풀(Pool)에 생성
// 	2) 풀에 저장된 커넥션 객체를 필요할 때 쓰고 반환
// 	3) 미리 생성하기 때문에 데이터베이스에 부하를 줄이고 유동적으로 연결을 관리

// DBCP 설치
// 1. ojcdbc6.jar 파일을 \webapp\WEB-INF\lib 폴더에 저장
// 2. 톰캣 설치경로 \lib 폴더에 있는 tomcat-dbcp.jar 파일을 \webapp\WEB-INF\lib 폴더에 저장
// 3. \META-INF\context.xml 파일에 Resource 태그 추가
// 4. \webapp\WEB-INF\web.xml 파일에 resource-ref 태그 추가

// DBCP를 사용한 Connection 연결/해제 유틸리티 클래스
public class ConnMgr {
	
	private ConnMgr() {
		
	}
	
	public static Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			DataSource ds = (DataSource) envContext.lookup("dbcp/orcl");
			conn = ds.getConnection();
			System.out.println("DBCP 연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn, Statement stmt){
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			close(conn,stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
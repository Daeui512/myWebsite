package edu.web.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.web.board.domain.BoardVO;
import edu.web.board.persistence.BoardDAO;
import edu.web.board.persistence.BoardDAOImple;

@WebServlet("*.do")	// *.do : ~/.do로 선언된 HTTP 호출에 반응
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static BoardDAO dao;
	private static final String BOARD_URL = "WEB-INF/board/";
	private static final String MAIN = "index";
	private static final String LIST = "list";
	private static final String REGISTER = "register";
	private static final String DETAIL = "detail";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String EXTENSION = ".jsp";
	private static final String SERVERSTR = ".do";
	
    public BoardController() {
    	dao = BoardDAOImple.getInstance();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	controlURI(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		controlURI(request, response);
	}
	
	private void controlURI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		System.out.println("호출 경로 : " + requestURI);
		System.out.println("호출 방식 : " + requestMethod);
		
		if (requestURI.contains(LIST + SERVERSTR)) {
			System.out.println("list 호출 확인");
			list(request,response);
		} else if (requestURI.contains(REGISTER + SERVERSTR)) {
			if (requestMethod.equals("GET")) {			// GET방식(페이지 불러오기)
				System.out.println("registerGET 호출 확인");
				registerGET(request, response);
			} else if (requestMethod.equals("POST")) {	// POST방식(DB에 저장)
				System.out.println("registerPOST 호출 확인");
				registerPOST(request, response);
			}
		} else if (requestURI.contains(DETAIL + SERVERSTR)) {
			System.out.println("detail 호출 확인");
			detail(request, response);
		} else if (requestURI.contains(UPDATE + SERVERSTR)) {
			if (requestMethod.equals("GET")) {
				System.out.println("updateGET 호출 확인");
				updateGET(request,response);
			} else if (requestMethod.equals("POST")) {
				updatePOST(request,response);
			}
		} else if (requestURI.contains(DELETE + SERVERSTR)) {
			System.out.println("delete 호출 확인");
			delete(request, response);
		}
	}

	// 게시판 전체 내용(LIST)을 DB에서 가져오고, 그 데이터를 list.jsp 페이지에 보내기
	private void list(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		List<BoardVO> list = dao.select();
		String path = BOARD_URL + LIST + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("list", list);
		dispatcher.forward(request, response);
	}// end of list
	
	// 글쓰기 버튼을 누르면 글쓰는 페이지를 불러옴
	private void registerGET(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String path = BOARD_URL + REGISTER + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		
	}// end of registerGET
	
	// 게시판 새글 데이터 DB등록 후, index.jsp로 이동
	private void registerPOST(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("userid");
		String userid = request.getParameter("userid");
		BoardVO vo = new BoardVO(0, title, content, userid, "");
		int result = dao.insert(vo);
		System.out.println(result);
		if (result ==1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" +"</head>");
			out.print("<script>alert('게시글 등록 성공');</script>");
			out.print("<script>location.href='"+ MAIN + EXTENSION +"'</script>");
		}
		
	}// end of registerPOST
	
	// bno 번호에 맞는 게시글 데이터를 DB에서 가져와서 detail.jsp에 전송
	// * detail.jsp에 updat.do 로 수정하기 버튼 생성
	private void detail(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo = dao.select(bno);
		System.out.println(vo);
		
		String path = BOARD_URL + DETAIL + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}// end of detail
	
	// bno 번호에 맞는 게시글 데이터를 DB에서 가져와서 update.jsp 에 전송
	// * update.jsp는 데이터 전송 <form action="update.do" method="post">으로 구성
	private void updateGET(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo = dao.select(bno);
		
		String path = BOARD_URL + UPDATE + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}// end of updateGET
	
	// bno 번호에 맞는 게시글 데이터를 DB에서 수정하고 index.jsp로 이동
	private void updatePOST(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		BoardVO vo = new BoardVO(bno, title, content, "", "");
		int result = dao.update(vo);
		System.out.println(result);
		
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" +"</head>");
			out.print("<script>alert('게시글 수정 성공');</script>");
			out.print("<script>location.href='"+ MAIN + EXTENSION +"'</script>");
		}
	}// end of updatePOST

	// 
	private void delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		int result = dao.delete(bno);
		System.out.println(result);
		if (result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" +"</head>");
			out.print("<script>alert('게시글 삭제 성공');</script>");
			out.print("<script>location.href='"+ MAIN + EXTENSION +"'</script>");
		}
	}// end of delete
}
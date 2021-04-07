<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${vo.title }</title>
</head>
<body>
  <h2>게시글 보기</h2>
  
  <hr>
  <a href="update.do?bno=${vo.bno }"><input type="button" value="수정하기"></a>
  <a href="delete.do?bno=${vo.bno }"><input type="button" value="게시글 삭제"></a><br>
  <hr>
  <div>
    <p>글 번호 : ${vo.bno }</p>
  </div>
  <div>
    <p>제목 <input type="text" value="${vo.title }" readonly="readonly"></p>
  </div>
  <div>
    <p>작성자 : ${vo.userid }</p>
    <p>작성일 : ${vo.cdate }</p>
  </div>
  <div>
    <textarea rows="20" cols="120" readonly="readonly">${vo.content }</textarea>
  </div>
  
</body>
</html>
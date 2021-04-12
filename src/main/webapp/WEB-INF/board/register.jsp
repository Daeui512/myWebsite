<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성 페이지</title>
</head>
<body>
  <h2>글 작성 페이지</h2>
  <form action="register.do" method="post">
    <div>
      <p>제목 :
        <input type="text" name="title" placeholder="제목" required="required">
      </p>
    </div>
    
    <div>
      <p>작성자 : ${userid }
        <input type="hidden" name="userid" value="${userid }" placeholder="작성자" required="required">
      </p>
    </div>
    
    <div>
      <textarea rows="20" cols="120" name="content" placeholder="내용 입력" 
      required="required"></textarea>
    </div>
    <div>
      <input type="submit" value="등록"> 
    </div>
  
  </form>
</body>
</html>
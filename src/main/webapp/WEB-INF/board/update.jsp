<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정 페이지</title>
</head>
<body>
  <h2>게시글 수정</h2>

  <hr>
  <form action="update.do" method="post">
    <div>
      <p>게시글 번호 : ${vo.bno }</p>
      <input type="hidden" name="bno" value="${vo.bno }">
    </div>
    <div>
      <p>
        제목 : <input type="text" name="title" placeholder="제목"
          value="${vo.title }" required="required">
      </p>
    </div>
    <div>
      <p>작성자 : ${vo.userid }</p>
      <p>작성일 : ${vo.cdate }</p>
    </div>
    <div>
      <textarea rows="20" cols="120" name="content" placeholder="내용 입력"
        required="required">${vo.content }</textarea>
    </div>
    <div>
      <input type="submit" value="수정">
    </div>
  </form>
</body>
</html>
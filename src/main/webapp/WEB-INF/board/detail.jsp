<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<title>${vo.title }</title>
</head>
<body>
  <h2>게시글 보기</h2>
  <hr>
  <c:if test="${userid == vo.userid }">
  <a href="update.do?bno=${vo.bno }"><input type="button" value="수정하기"></a>
  <a href="delete.do?bno=${vo.bno }"><input type="button" value="게시글 삭제"></a><br>
  </c:if>
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
  
  <hr>
  <c:if test="${empty userid }">
     * 댓글은 로그인이 필요한 서비스 입니다.
     <a href="login.go">로그인하기</a>
  </c:if>
  <c:if test="${not empty userid }">
    ${userid }님, 이제 댓글을 작성할 수 있습니다.
  
  </c:if>
  <div style="text-align: center">
    <div>
      <input type="hidden" id="replyBno" value="${vo.bno }">
      <c:if test="${not empty userid }">
        <input type="text" id="replyId" value="${userid }" readonly>
        <input type="text" id="replyContent">
        <button type="button" id="btn_add">작성</button>   
      </c:if>
    </div>
  </div>
  
  <hr>
  <div style="text-align : center;">
    <div id="replies"></div>
  </div>
  
  <script type="text/javascript">
  	$(document).ready(function(){
  		var replyBno = $('#replyBno').val();	// id = replyBno의 value값을 가져옴
  		getAllReplies();
  		$('#btn_add').click(function(){				// 게시판 번호, 댓글 아이디, 댓글 내용의 값을 가져와서 post 방식으로 전송
      		var replyContent = $('#replyContent').val();
      		var replyId = $('#replyId').val();
      		
  			$.ajax({
  				type : 'post',
  				url : 'replies/add',
				data : {
	  				'replyBno' : replyBno,
	  				'replyContent' : replyContent,
	  	 			'replyId' : replyId
	  			},
				success : function(result){
					if(result == 'success'){
						alert('댓글 입력 성공');
						$('#replyContent').val('');
						//댓글이 입력이 완료되면 replyContent value 초기화
						getAllReplies();	//전체 댓글 호출
					}
				}
  			})// end of ajax();
  		})// end of btn_add.click()
  		
  		// 게시판의 댓글 전체 가져오기
  		function getAllReplies(){
  			var url = 'replies/all?replyBno=' + replyBno;
  			$.getJSON(
  				url,
  				function(jsonData){
  					//jsonData : list 데이터가 저장되어 있음
  					console.log(jsonData);
					var writer = $('#replyId').val();
  					var list = '';  //JSON 데이터를 표현할 변수
  					// $(collection).each(); : //컬렉션 데이터를 반복문으로 꺼내는 함수
  					$(jsonData).each(function(){
  						// this : collection에서 각 데이터를 꺼내서 저장
  						console.log(this);
  						var replyDate = new Date(this.replyDate);
  						var disabled = 'disabled';
  						var readonly = 'readonly';
  						if(writer == this.replyId){
							disabled = '';  	
							readonly = '';
  						}
  						
  						list += '<div class="reply_item">'
  							+ '<pre>'
  							+ '<input type="hidden" id="replyNo" value="' + this.replyNo + '" />'
  							+ '<input type="hidden" id="replyId" value="' + this.replyId + '" />'
  							+ this.replyId 
  							+ '&nbsp;&nbsp;'	//공백
  							+ '<input type="text" id="replyContent" value="' + this.replyContent + '" ' + readonly + '/>'
  							+ '&nbsp;&nbsp;'
  							+ replyDate
  							+ '&nbsp;&nbsp;'
  							+ '<button class="btn_update" type="button"' + disabled + '>수정</button>'
  							+ '<button class="btn_delete" type="button"' + disabled + '>삭제</button>'
  							+ '</pre>'
  							+ '</div>';
  					});// end each()
  					$('#replies').html(list)
  				}// end function()
  			); // end getJSON()
  		}// end of getAllReplies()
  		
  		// 수정 버튼을 클릭하면 선택된 댓글 수정
  		$('#replies').on('click', '.reply_item .btn_update', function(){
  			console.log(this);
  			// 선택된 댓글 replyNo, replyContent 값을 저장
  			// prevAll() : 선택된 노드 이전의 모든 형제 노드
  			var replyNo = $(this).prevAll('#replyNo').val();
  			var replyContent = $(this).prevAll('#replyContent').val();
			console.log('선택된 댓글 번호 : ' + replyNo + ', 댓글 내용 : ' + replyContent);  			
			$.ajax({
				type : 'get',
				url : 'replies/update?replyNo=' + replyNo,
				data : {
					'replyBno' : replyBno,
					'replyContent' : replyContent
				},
				success : function(result){
					if(result == 'success'){
						alert('댓글 수정 완료');
						getAllReplies();
					}
				} //end success
			})// end of .ajax()			
  		});	// end of replies.on.update()
  		
  		$('#replies').on('click', '.reply_item .btn_delete', function(){
			var replyNo = $(this).prevAll('#replyNo').val();
			$.ajax({
				type : 'get',
				url : 'replies/delete?replyNo=' + replyNo,
				data : {
					'replyBno' : replyBno
				},
				success : function(result){
					if(result == 'success'){
						alert('댓글 삭제 완료');
						getAllReplies();
					}
				}// end of success()
			});// end of ajax()
  		});// end of replies.on.delete()
  		
  	});// end of document.ready()
  </script>
</body>
</html>
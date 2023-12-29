<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="layout/header.jsp"></jsp:include>
<h1>
	Hello world!  
</h1>

<div>
<c:if test="${ses.id ne null }">
	<p>${ses.id } 님 안녕하세요~!!<span class="badge text-bg-primary">${ses.last_login }</span></p>
</c:if>
</div>

<script type="text/javascript">
	const msg_login = `<c:out value="${msg_login}" />`;
	const msg_logout = `<c:out value="${msg_logout}" />`;
	const msg_modify = `<c:out value="${msg_modify}" />`;
	const msg_delete = `<c:out value="${msg_delete}" />`;
	if(msg_login === "1"){
		alert("로그인 실패~!!!");
	}
	if(msg_modify === "1"){
		alert("회원정보가 수정되었습니다. 다시 로그인해주세요.");
	}
	if(msg_delete === "1"){
		alert("회원탈퇴가 완료되었습니다..");
	}
	if(msg_logout === "1"){
		alert("로그아웃되었습니다.");
	}
</script>

<jsp:include page="layout/footer.jsp"></jsp:include>
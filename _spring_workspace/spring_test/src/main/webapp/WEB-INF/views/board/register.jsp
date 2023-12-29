<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 헤더 -->
<jsp:include page="../layout/header.jsp"></jsp:include>
<br>
<div class="container-md">
<h2>Board Register Page</h2>
<br>
<form action="/board/register" method="post" enctype="multipart/form-data">
	<div class="mb-3">
		<label for="title" class="form-label">Title</label>
		<input type="text" name="title" class="form-control" id="title" placeholder="Title..">
	</div>
	<div class="mb-3">
		<label for="writer" class="form-label">Writer</label>
		<input type="text" name="writer" class="form-control" id="writer" value="${ses.id }" readonly="readonly">
	</div>
	<div class="mb-3">
		<label for="content" class="form-label">Content</label>
		<textarea class="form-control" name="content" rows="3" id="content" placeholder="Content.."></textarea>
	</div>
	
	<!-- file 입력 라인 추가 -->
	<div class="mb-3">
		<label for="file" class="form-label">Files</label>
		<input type="file" name="files" class="form-control" id="file" multiple style="display: none"> <br>
		<button type="button" class="btn btn-primary" id="trigger">FileUpload</button>
	</div>
	<!-- 파일 목록 표시라인 -->
	<div class="mb-3" id="fileZone">
	
	</div>
	<button type="submit" class="btn btn-primary" id="regBtn">register</button>
</form>
</div>

<script src="/resources/js/boardRegister.js"></script>
<!-- 푸터 -->
<jsp:include page="../layout/footer.jsp"></jsp:include>
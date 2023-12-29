<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<jsp:include page="../layout/header.jsp"></jsp:include>


<div class="container-md">
<h2>Member Modify Page</h2><br>

<%-- <form action="/member/modify" method="post">
	<div class="mb-3">
		<label for="id" class="form-label">ID</label>
		<input type="text" name="id" class="form-control" id="id" value="${ses.id }" readonly="readonly">
	</div>
	<div class="mb-3">
		<label for="pw" class="form-label">Password</label>
		<input type="password" name="pw" class="form-control" id="pw">
	</div>
	<div class="mb-3">
		<label for="name" class="form-label">Name</label>
		<input type="text" name="name" class="form-control" id="name" value="${ses.name }">
	</div>
	<div class="mb-3">
		<label for="email" class="form-label">Email</label>
		<input type="email" name="email" class="form-control" id="email" value="${ses.email }">
	</div>
	<div class="mb-3">
		<label for="home" class="form-label">Home</label>
		<input type="text" name="home" class="form-control" id="home" value="${ses.home }">
	</div>
	<div class="mb-3">
		<label for="age" class="form-label">Age</label>
		<input type="text" name="age" class="form-control" id="age" value="${ses.age }">
	</div>
	<button type="submit" class="btn btn-primary">Modify</button>
	<a href="/index.jsp"><button type="button" class="btn btn-primary">index</button></a>
</form> --%>

<form action="/member/modify" method="post">	
<table class="table">
  <tbody>
    <tr>
	<input type="hidden" name="id" value="${ses.id }"> 
      <th>ID</th>
      <td>${ses.id }</td>
    </tr>
    <tr>
      <th>Password</th>
      <td> <input type="password" name="pw" placeholder="변경할 password..."> </td>
    </tr>
    <tr>
      <th>Name</th>
      <td> <input type="text" name="name" value="${ses.name }"> </td>
    </tr>
    <tr>
      <th>Email</th>
      <td><input type="email" name="email" value="${ses.email }"></td>
    </tr>
    <tr>
      <th>Home</th>
      <td><input type="text" name="home" value="${ses.home }"></td>
    </tr>
    <tr>
      <th>Age</th>
      <td><input type="text" name="age" value="${ses.age }"></td>
    </tr>
  </tbody>
  </table>
  <button type="submit" class="btn btn-primary">modify</button>
  <a id="a"><button type="button" class="btn btn-danger" id="del">delete</button></a>
</form>
</div>

<script type="text/javascript">
	let del = document.getElementById('del');
	let a = document.getElementById('a');
	del.addEventListener('click', () => {
   		 if (confirm('삭제하시겠습니까?')) {
       		 a.setAttribute("href", "/member/delete");
   		 }
	})
</script>

<jsp:include page="../layout/header.jsp"></jsp:include>
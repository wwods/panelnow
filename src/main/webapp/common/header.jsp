<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Member" %>

<%
  Member user = (Member) session.getAttribute("user");
%>

<!-- ✅ Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- ✅ Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand fw-bold" href="<%= request.getContextPath() %>/">패널나우</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
            aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav ms-auto">
        

        <% if (user != null && user.is관리자여부()) { %>
          <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath() %>/product/add.jsp">상품 등록</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath() %>/admin">설문 관리</a>
          </li>
          <li class="nav-item">
    		<a class="nav-link" href="<%= request.getContextPath() %>/survey/addSurvey.jsp">설문 추가</a>
  		  </li>
        <% } %>

        <% if (user != null) { %>
          <li class="nav-item">
  			<a class="nav-link" href="<%= request.getContextPath() %>/userAction?action=mypage">마이페이지</a>
		  </li>
          <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath() %>/auth">로그아웃</a>
          </li>
        <% } else { %>
          <li class="nav-item">
            <a class="nav-link" href="<%= request.getContextPath() %>/auth/login.jsp">로그인</a>
          </li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Member" %>
<%@ page import="model.Product" %>
<%@ page import="java.util.List" %>

<%
  Member user = (Member) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    return;
  }

  List<Product> purchased = (List<Product>) request.getAttribute("purchased");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">👤 마이페이지</h2>

  <div class="card mb-4">
    <div class="card-body">
      <h5 class="card-title">환영합니다, <%= user.get닉네임() %>님!</h5>
      <p class="card-text">이메일: <%= user.get이메일() %></p>
      <p class="card-text">보유 포인트: <strong><%= user.get포인트() %>P</strong></p>
    </div>
  </div>

  <h4 class="mt-4">🛍️ 구매한 상품 목록</h4>

  <% if (purchased != null && !purchased.isEmpty()) { %>
    <ul class="list-group mt-2">
      <% for (Product p : purchased) { %>
        <li class="list-group-item">
          <strong><%= p.get상품명() %></strong> - <%= p.get가격() %>P<br/>
          <small class="text-muted"><%= p.get설명() %></small>
        </li>
      <% } %>
    </ul>
  <% } else { %>
    <p class="text-muted mt-2">📭 아직 구매한 상품이 없습니다.</p>
  <% } %>

  <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary mt-4">홈으로 돌아가기</a>
</div>
</body>
</html>

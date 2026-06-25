<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Member" %>
<%@ page import="model.Product" %>
<%@ page import="java.util.List" %>
<%@ include file="/common/header.jsp" %>

<%
  if (user == null) {
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    return;
  }

  List<Product> products = (List<Product>) request.getAttribute("products");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>상품 목록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-3">🛒 상품 목록</h2>
  <p>보유 포인트: <%= user.get포인트() %></p>

  <% if (products != null) { %>
    <% for (Product p : products) { %>
      <div style="margin:10px 0; padding:10px; border:1px solid #ccc;">
      <% System.out.println("상품ID 디버그: " + p.get상품ID()); %>
        <h4><%= p.get상품명() %> (<%= p.get가격() %>P)</h4>
        <p><%= p.get설명() %></p>
        <form action="<%= request.getContextPath() %>/product" method="post" style="display:inline;">
          <input type="hidden" name="상품ID" value="<%= p.get상품ID() %>" />
          <button type="submit" class="btn btn-sm btn-primary">구매하기</button>
        </form>
      </div>
    <% } %>
  <% } else { %>
    <p class="text-muted">상품 목록이 없습니다.</p>
  <% } %>

</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Member, model.Survey, model.Product" %>
<%@ include file="/common/header.jsp" %>

<%
  if (user == null || !user.is관리자여부()) {
%>
  <div class="container mt-5">
    <div class="alert alert-danger text-center">⚠️ 관리자만 접근 가능합니다.</div>
  </div>
<%
    return;
  }
%>

<html>
<head>
  <title>관리자 페이지</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">

  <h2 class="mb-4">📋 설문 목록</h2>

  <table class="table table-bordered table-hover">
    <thead class="table-light">
      <tr>
        <th>ID</th>
        <th>제목</th>
        <th>설명</th>
        <th>관리</th>
      </tr>
    </thead>
    <tbody>
    <%
      java.util.List<Survey> surveys = (java.util.List<Survey>) request.getAttribute("surveys");
      if (surveys != null && !surveys.isEmpty()) {
        for (Survey s : surveys) {
    %>
      <tr>
        <td><%= s.get설문ID() %></td>
        <td><%= s.get제목() %></td>
        <td><%= s.get설명() %></td>
        <td>
          <a href="/survey/editSurvey.jsp?설문ID=<%= s.get설문ID() %>&제목=<%= s.get제목() %>&설명=<%= s.get설명() %>" class="btn btn-sm btn-primary">수정</a>
          <a href="<%= request.getContextPath() %>/statistics?설문ID=<%= s.get설문ID() %>" class="btn btn-sm btn-info">통계 보기</a>
        </td>
      </tr>
    <%
        }
      } else {
    %>
      <tr><td colspan="4" class="text-center">등록된 설문이 없습니다.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

  <hr class="my-5"/>

  <h2 class="mb-4">🛍️ 상품 목록</h2>

  <table class="table table-bordered table-hover">
    <thead class="table-light">
      <tr>
        <th>ID</th>
        <th>상품명</th>
        <th>가격</th>
        <th>관리</th>
      </tr>
    </thead>
    <tbody>
    <%
      java.util.List<Product> products = (java.util.List<Product>) request.getAttribute("products");
      if (products != null && !products.isEmpty()) {
        for (Product p : products) {
    %>
      <tr>
        <td><%= p.get상품ID() %></td>
        <td><%= p.get상품명() %></td>
        <td><%= p.get가격() %></td>
        <td>
          <a href="admin?action=deleteProduct&상품ID=<%= p.get상품ID() %>" class="btn btn-sm btn-danger">삭제</a>
        </td>
      </tr>
    <%
        }
      } else {
    %>
      <tr><td colspan="4" class="text-center">등록된 상품이 없습니다.</td></tr>
    <%
      }
    %>
    </tbody>
  </table>

</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ include file="/common/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>설문 통계 결과</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">📊 설문 통계</h2>

  <%
    Map<String, Integer> stats = (Map<String, Integer>) request.getAttribute("stats");
    String 질문내용 = (String) request.getAttribute("질문내용"); // 질문도 함께 표시
  %>

  <h4 class="mb-3">📝 질문: <%= 질문내용 %></h4>

  <% if (stats != null && !stats.isEmpty()) { %>
    <table class="table table-bordered">
      <thead>
        <tr>
          <th>보기 내용</th>
          <th>응답 수</th>
        </tr>
      </thead>
      <tbody>
        <% for (Map.Entry<String, Integer> entry : stats.entrySet()) { %>
          <tr>
            <td><%= entry.getKey() %></td>
            <td><%= entry.getValue() %></td>
          </tr>
        <% } %>
      </tbody>
    </table>
  <% } else { %>
    <div class="alert alert-warning">📭 통계 데이터가 없습니다.</div>
  <% } %>

  <a href="<%= request.getContextPath() %>/survey/manage.jsp" class="btn btn-secondary mt-3">돌아가기</a>
</div>
</body>
</html>

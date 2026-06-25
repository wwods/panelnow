<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Question" %>
<%@ page import="model.Choice" %>
<%@ page import="model.Survey" %>
<%@ include file="/common/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>설문 목록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">📋 설문 목록</h2>

  <ul class="list-group">
  <%
    List<model.Survey> surveys = (List<model.Survey>) request.getAttribute("surveys");
    if (surveys != null && !surveys.isEmpty()) {
      for (model.Survey s : surveys) {
  %>
    <li class="list-group-item">
      <a href="<%= request.getContextPath() %>/SurveyController?id=<%= s.get설문ID() %>" class="text-decoration-none">
        <strong><%= s.get제목() %></strong>
        <div class="text-muted small"><%= s.get설명() %></div>
      </a>
    </li>
  <%
      }
    } else {
  %>
    <li class="list-group-item text-danger">설문 목록이 없습니다.</li>
  <%
    }
  %>
  </ul>
</div>
</body>
</html>

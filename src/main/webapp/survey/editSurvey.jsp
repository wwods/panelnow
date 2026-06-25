<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Member" %>

<%
  Member user = (Member) session.getAttribute("user");
  if (user == null || !user.is관리자여부()) {
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    return;
  }

  String 설문ID = request.getParameter("설문ID");
  String 제목 = request.getParameter("제목");
  String 설명 = request.getParameter("설명");

  if (설문ID == null) {
    response.sendRedirect(request.getContextPath() + "/admin");
    return;
  }

  // 디버깅 로그 출력 (개발 중 확인용)
  System.out.println("DEBUG: 설문ID=" + 설문ID + ", 제목=" + 제목 + ", 설명=" + 설명);
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>설문 수정</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4 fw-bold">📝 설문 수정</h2>

  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input type="hidden" name="action" value="editSurvey">
    <input type="hidden" name="설문ID" value="<%= 설문ID %>">

    <div class="mb-3">
      <label for="제목" class="form-label">제목</label>
      <input type="text" class="form-control" name="제목" id="제목"
             value="<%= 제목 != null ? 제목 : "" %>" required>
    </div>

    <div class="mb-3">
      <label for="설명" class="form-label">설명</label>
      <textarea class="form-control" name="설명" id="설명" rows="4" required><%= 설명 != null ? 설명 : "" %></textarea>
    </div>

    <div class="d-flex">
      <button type="submit" class="btn btn-primary me-2">수정</button>
      <a href="<%= request.getContextPath() %>/admin" class="btn btn-secondary">취소</a>
    </div>
  </form>
</div>
</body>
</html>

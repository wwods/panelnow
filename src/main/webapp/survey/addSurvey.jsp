<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Member" %>
<%@ include file="/common/header.jsp" %>

<%
  if (user == null || !user.is관리자여부()) {
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
    return;
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>설문 등록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">📋 설문 등록</h2>

  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input type="hidden" name="action" value="addSurvey" />

    <div class="mb-3">
      <label for="제목" class="form-label">제목</label>
      <input type="text" class="form-control" id="제목" name="제목" required />
    </div>

    <div class="mb-3">
      <label for="설명" class="form-label">설명</label>
      <textarea class="form-control" id="설명" name="설명" required></textarea>
    </div>

    <div class="mb-3">
      <label for="질문내용" class="form-label">질문 내용</label>
      <input type="text" class="form-control" id="질문내용" name="질문내용" required />
    </div>

    <div class="mb-3">
      <label class="form-label">보기 내용</label>
      <input type="text" class="form-control mb-1" name="보기내용" placeholder="보기 1" />
      <input type="text" class="form-control mb-1" name="보기내용" placeholder="보기 2" />
      <input type="text" class="form-control mb-1" name="보기내용" placeholder="보기 3" />
      <input type="text" class="form-control mb-1" name="보기내용" placeholder="보기 4" />
      <input type="text" class="form-control mb-1" name="보기내용" placeholder="보기 5" />
    </div>

    <button type="submit" class="btn btn-primary">등록하기</button>
    <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary">취소</a>
  </form>
</div>
</body>
</html>

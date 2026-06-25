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
  <title>상품 등록</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-4">🛍️ 상품 등록</h2>

  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input type="hidden" name="action" value="addProduct" />

    <div class="mb-3">
      <label for="상품명" class="form-label">상품명</label>
      <input type="text" class="form-control" name="상품명" id="상품명" required />
    </div>
    
    <div class="mb-3">
  		<label for="설명" class="form-label">설명</label>
  		<textarea class="form-control" name="설명" id="설명" rows="3"></textarea>
	</div>

    <div class="mb-3">
      <label for="가격" class="form-label">가격</label> <!-- 수정됨 -->
      <input type="number" class="form-control" name="가격" id="가격" min="0" required /> <!-- 수정됨 -->
    </div>

    <button type="submit" class="btn btn-primary">등록하기</button>
    <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-secondary">취소</a>
  </form>
</div>
</body>
</html>

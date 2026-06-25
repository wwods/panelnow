<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원가입</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5" style="max-width: 500px;">
  <div class="card shadow-sm">
    <div class="card-body">
      <h3 class="card-title mb-4 text-center">회원가입</h3>

      <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="mb-3">
          <label class="form-label">이메일</label>
          <input type="email" name="email" class="form-control" placeholder="이메일" required>
        </div>
        <div class="mb-3">
          <label class="form-label">비밀번호</label>
          <input type="password" name="password" class="form-control" placeholder="비밀번호" required>
        </div>
        <div class="mb-3">
          <label class="form-label">닉네임</label>
          <input type="text" name="nickname" class="form-control" placeholder="닉네임" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">회원가입</button>
      </form>

      <div class="text-center mt-3">
        <a href="login.jsp">이미 계정이 있으신가요? 로그인</a>
      </div>
    </div>
  </div>
</div>

</body>
</html>

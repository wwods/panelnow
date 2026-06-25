<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jsp" %>

<h1>🎉 패널나우 설문 시스템에 오신 것을 환영합니다!</h1>
<p class="lead">다양한 설문에 참여하고 포인트를 모아 상품도 구매해보세요.</p>

<hr>

<div class="row">
  <div class="col-md-6">
    <h4>📝 설문 참여하기</h4>
    <p>등록된 설문에 참여하면 포인트를 받을 수 있어요.</p>
    <a href="${pageContext.request.contextPath}/SurveyController" class="btn btn-success">설문 목록 보기</a>
  </div>
  <div class="col-md-6">
    <h4>🛍️ 상품 구매하기</h4>
    <p>포인트로 다양한 상품을 구매할 수 있어요.</p>
    <a href="${pageContext.request.contextPath}/product" class="btn btn-success">상품 목록 보기</a>
  </div>
</div>

<%@ include file="common/footer.jsp" %>

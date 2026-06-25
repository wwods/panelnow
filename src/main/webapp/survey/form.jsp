<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Survey, model.Question, model.Choice, java.util.*" %>

<%
  Survey survey = (Survey) request.getAttribute("survey");
  Map<Question, List<Choice>> questionChoiceMap = (Map<Question, List<Choice>>) request.getAttribute("questionChoiceMap");
%>

<html>
<head>
  <title>설문 참여</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2 class="mb-3"><%= survey.get제목() %></h2>
  <p class="text-muted mb-4"><%= survey.get설명() %></p>

  <form action="<%= request.getContextPath() %>/submitResponse" method="post">
    <input type="hidden" name="설문ID" value="<%= survey.get설문ID() %>" />

    <%
      for (Map.Entry<Question, List<Choice>> entry : questionChoiceMap.entrySet()) {
        Question question = entry.getKey();
        List<Choice> choices = entry.getValue();
    %>
      <div class="mb-4">
        <label class="form-label fw-bold"><%= question.get질문내용() %></label>

        <%
          if (true) {
            for (Choice choice : choices) {
        %>
          <div class="form-check">
            <input class="form-check-input" type="radio"
                   name="question_<%= question.get질문ID() %>"
                   value="<%= choice.get보기ID() %>" required>
            <label class="form-check-label"><%= choice.get내용() %></label>
          </div>
        <%
            }
          }
        %>
        <%
          }
        %>
      </div>

    <button type="submit" class="btn btn-primary">제출하기</button>
  </form>
</div>
</body>
</html>

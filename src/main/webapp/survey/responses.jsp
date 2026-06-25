<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<h2>📋 설문 응답 내역 보기</h2>

<%
  List<Map<String, String>> 응답목록 = (List<Map<String, String>>) request.getAttribute("응답목록");
  if (응답목록 == null || 응답목록.isEmpty()) {
%>
  <p>응답이 없습니다.</p>
<%
  } else {
%>
  <table border="1" cellpadding="5" cellspacing="0">
    <tr>
      <th>닉네임</th>
      <th>제출시간</th>
      <th>질문</th>
      <th>응답</th>
    </tr>
    <% for (Map<String, String> row : 응답목록) { %>
      <tr>
        <td><%= row.get("닉네임") %></td>
        <td><%= row.get("제출시간") %></td>
        <td><%= row.get("질문") %></td>
        <td><%= row.get("응답") %></td>
      </tr>
    <% } %>
  </table>
<% } %>

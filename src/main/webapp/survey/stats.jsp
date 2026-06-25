<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
  <title>설문 통계</title>
  <!-- ✅ Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <!-- ✅ Google Charts 로드 -->
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<div class="container mt-5">

  <h2 class="mb-4">📊 설문 통계 보기 - <%= request.getAttribute("제목") %></h2>

  <!-- ✅ 관리자 페이지로 돌아가는 버튼 -->
  <form action="<%= request.getContextPath() %>/admin" method="get" class="mb-4">
    <button type="submit" class="btn btn-secondary">← 관리자 페이지로 돌아가기</button>
  </form>

  <%
    Map<String, Map<String, Integer>> 통계 = (Map<String, Map<String, Integer>>) request.getAttribute("통계");

    if (통계 == null || 통계.isEmpty()) {
  %>
    <div class="alert alert-info">응답 통계가 없습니다.</div>
  <%
    } else {
      int chartIndex = 0;
      for (Map.Entry<String, Map<String, Integer>> 질문 : 통계.entrySet()) {
        String 질문내용 = 질문.getKey();
        Map<String, Integer> 보기통계 = 질문.getValue();
  %>

    <h4 class="mt-5"><%= 질문내용 %></h4>

    <!-- ✅ 텍스트 표 출력 -->
    <table class="table table-bordered mt-2">
      <thead class="table-light">
        <tr><th>보기</th><th>응답 수</th></tr>
      </thead>
      <tbody>
      <% for (Map.Entry<String, Integer> 보기 : 보기통계.entrySet()) { %>
        <tr>
          <td><%= 보기.getKey() %></td>
          <td><%= 보기.getValue() %></td>
        </tr>
      <% } %>
      </tbody>
    </table>

    <!-- ✅ 차트 출력 영역 -->
    <div id="chart_<%= chartIndex %>" style="width: 100%; height: 400px; margin-top: 20px;"></div>

    <!-- ✅ 차트 스크립트 -->
    <script type="text/javascript">
      google.charts.load('current', {packages: ['corechart']});
      google.charts.setOnLoadCallback(drawChart<%= chartIndex %>);

      function drawChart<%= chartIndex %>() {
        var data = google.visualization.arrayToDataTable([
          ['보기', '응답 수'],
          <% for (Map.Entry<String, Integer> 보기 : 보기통계.entrySet()) { %>
            ['<%= 보기.getKey() %>', <%= 보기.getValue() %>],
          <% } %>
        ]);

        var options = {
          title: '<%= 질문내용 %>',
          legend: { position: 'none' },
          hAxis: { title: '보기' },
          vAxis: { title: '응답 수', minValue: 0 }
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_<%= chartIndex %>'));
        chart.draw(data, options);
      }
    </script>

    <hr>
  <%
        chartIndex++;
      }
    }
  %>

</div>
</body>
</html>

package controller;

import dao.SurveyDAO;
import dao.QuestionDAO;
import dao.ChoiceDAO;
import dao.AdminDAO;
import model.Survey;
import model.Question;
import model.Choice;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

@WebServlet("/SurveyController")
public class SurveyController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        String action = request.getParameter("action");

        try (Connection conn = DBUtil.getConnection()) {

            if ("responses".equals(action) && idParam != null) {
                // ✅ 설문 응답 보기
                int 설문ID = Integer.parseInt(idParam);
                AdminDAO dao = new AdminDAO(conn);
                List<Map<String, String>> 응답목록 = dao.getSurveyResponses(설문ID);
                request.setAttribute("응답목록", 응답목록);
                request.getRequestDispatcher("/survey/responses.jsp").forward(request, response);
                return;
            }

            if (idParam != null) {
                // ✅ 설문 참여 폼
                int 설문ID = Integer.parseInt(idParam);

                SurveyDAO surveyDAO = new SurveyDAO(conn);
                QuestionDAO questionDAO = new QuestionDAO(conn);
                ChoiceDAO choiceDAO = new ChoiceDAO(conn);

                Survey survey = surveyDAO.getSurveyById(설문ID);
                List<Question> questions = questionDAO.getQuestionsBySurveyId(설문ID);

                Map<Question, List<Choice>> questionChoiceMap = new LinkedHashMap<>();
                for (Question q : questions) {
                    List<Choice> choices = choiceDAO.getChoicesByQuestionId(q.get질문ID());
                    questionChoiceMap.put(q, choices);
                }

                request.setAttribute("survey", survey);
                request.setAttribute("questionChoiceMap", questionChoiceMap);

                request.getRequestDispatcher("/survey/form.jsp").forward(request, response);
            } else {
                // ✅ 설문 목록
                SurveyDAO surveyDAO = new SurveyDAO(conn);
                List<Survey> surveys = surveyDAO.getAllSurveys();
                request.setAttribute("surveys", surveys);
                request.getRequestDispatcher("/survey/list.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}

package com.shubham.quizservice.service;

import com.shubham.quizservice.dao.QuizDao;
import com.shubham.quizservice.feign.QuizInterface;
import com.shubham.quizservice.model.QuestionWrapper;
import com.shubham.quizservice.model.Quiz;
import com.shubham.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
//    @Autowired
//    IQuestionDao questionDao;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {


//          List<Integer> questions = //call the generate URL - RestTemplate http://localhost:8080/question/generate
        List<Integer> quesions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(quesions);
        quizDao.save(quiz);
        return  new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
          Quiz quiz = quizDao.findById(id).get();

          List<Integer> questionIds = quiz.getQuestionIds();
          ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionFromId(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
          ResponseEntity<Integer> score = quizInterface.getScore(responses);

        return score;
    }
}

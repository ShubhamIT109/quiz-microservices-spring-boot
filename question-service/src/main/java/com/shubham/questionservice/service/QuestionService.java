package com.shubham.questionservice.service;

import com.shubham.questionservice.dao.IQuestionDao;
import com.shubham.questionservice.model.Question;
import com.shubham.questionservice.model.QuestionWrapper;
import com.shubham.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    IQuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("BAD Request", HttpStatus.BAD_REQUEST);

    }

    public Question getQuestionById(Integer id) {
        return questionDao.findById(id).orElse(null);
    }

    public void deleteQuestionById(Integer id) {
        questionDao.deleteById(id);
    }

    public String updateQuestion(Integer id, Question updatedQuestion) {

        Question existingQuestion = questionDao.findById(id).orElse(null);

        if (existingQuestion == null) {
            return "Question not found";
        }

        existingQuestion.setQuestionTittle(updatedQuestion.getQuestionTittle());
        existingQuestion.setOption1(updatedQuestion.getOption1());
        existingQuestion.setOption2(updatedQuestion.getOption2());
        existingQuestion.setOption3(updatedQuestion.getOption3());
        existingQuestion.setOption4(updatedQuestion.getOption4());
        existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
        existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
        existingQuestion.setCategory(updatedQuestion.getCategory());

        questionDao.save(existingQuestion);

        return "Question updated successfully";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Integer id:questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question:questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTittle(question.getQuestionTittle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for(Response response:responses){
            Question question = questionDao.findById(response.getId()).get();

            if(response.getResponse().equals(question.getRightAnswer())) {
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

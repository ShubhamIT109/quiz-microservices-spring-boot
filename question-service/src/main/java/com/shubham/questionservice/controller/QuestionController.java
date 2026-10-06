package com.shubham.questionservice.controller;

import com.shubham.questionservice.model.Question;
import com.shubham.questionservice.model.QuestionWrapper;
import com.shubham.questionservice.model.Response;
import com.shubham.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id,@RequestBody Question updatedQuestion){
        String response = questionService.updateQuestion(id,updatedQuestion);
        if(response.equals("Question not found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Integer id){
        Question question = questionService.getQuestionById(id);
        if(question!=null) {
            questionService.deleteQuestionById(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@RequestParam String categoryName, @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    //getQuestions(questionid)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionFromId(questionIds);
    }
    //getScore
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }


}


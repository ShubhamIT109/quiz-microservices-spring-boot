package com.shubham.quizservice.controller;

import com.shubham.quizservice.model.QuestionWrapper;
import com.shubham.quizservice.model.QuizDto;
import com.shubham.quizservice.model.Response;
import com.shubham.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return (quizService.createQuiz(quizDto.getCategory(),quizDto.getNumQuestions(),quizDto.getTitle()));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
       return quizService.getQuizQuestion(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return  quizService.calculateResult(id,responses);
    }

}

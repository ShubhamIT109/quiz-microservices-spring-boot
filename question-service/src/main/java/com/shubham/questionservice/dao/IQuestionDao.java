package com.shubham.questionservice.dao;

import com.shubham.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionDao extends JpaRepository<Question,Integer> {
  List<Question> findByCategory(String Category);
  @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
  List<Integer> findRandomQuestionByCategory(String category, int numQ);
}

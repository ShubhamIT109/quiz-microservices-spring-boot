package com.shubham.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private  String title;
    @ElementCollection
    private List<Integer> questionIds;
}

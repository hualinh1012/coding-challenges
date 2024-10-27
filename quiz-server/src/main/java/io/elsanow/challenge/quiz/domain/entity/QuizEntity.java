package io.elsanow.challenge.quiz.domain.entity;

import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
public class QuizEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions;

    public QuizEntity(String referenceId, String title, String description, List<QuestionEntity> questions) {
        this.referenceId = referenceId;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }
}
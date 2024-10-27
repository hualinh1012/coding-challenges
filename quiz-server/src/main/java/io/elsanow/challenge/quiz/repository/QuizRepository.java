package io.elsanow.challenge.quiz.repository;

import io.elsanow.challenge.quiz.domain.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    QuizEntity findByReferenceId(String referenceId);
}
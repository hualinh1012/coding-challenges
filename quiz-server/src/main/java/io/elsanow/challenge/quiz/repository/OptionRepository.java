package io.elsanow.challenge.quiz.repository;

import io.elsanow.challenge.quiz.domain.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
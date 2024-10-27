# CREATE TABLE users
# (
#     id         BIGINT AUTO_INCREMENT PRIMARY KEY,
#     username   VARCHAR(50)  NOT NULL UNIQUE,
#     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
# );

CREATE TABLE quizzes
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference_id VARCHAR(100) NOT NULL,
    title        VARCHAR(100) NOT NULL,
    description  TEXT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reference_id ON quizzes(reference_id);

CREATE TABLE questions
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    quiz_id       BIGINT                                                 NOT NULL,
    question_text TEXT                                                   NOT NULL,
    question_type ENUM ('MULTIPLE_CHOICE', 'TRUE_FALSE', 'SHORT_ANSWER') NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE CASCADE
);

CREATE TABLE options
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT       NOT NULL,
    option_text VARCHAR(255) NOT NULL,
    is_correct  BOOLEAN   DEFAULT FALSE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE
);

# CREATE TABLE answers
# (
#     id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
#     question_id        BIGINT NOT NULL,
#     user_id            BIGINT NOT NULL,
#     selected_option_id BIGINT,
#     user_answer        TEXT,
#     created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
#     FOREIGN KEY (question_id) REFERENCES questions (id) ON DELETE CASCADE,
#     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
#     FOREIGN KEY (selected_option_id) REFERENCES options (id) ON DELETE SET NULL
# );
#
# CREATE TABLE leaderboard
# (
#     id         BIGINT AUTO_INCREMENT PRIMARY KEY,
#     quiz_id    BIGINT NOT NULL,
#     user_id    BIGINT NOT NULL,
#     score      INT    NOT NULL,
#     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
#     FOREIGN KEY (quiz_id) REFERENCES quizzes (id) ON DELETE CASCADE,
#     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
# );
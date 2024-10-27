INSERT INTO quizzes (id, reference_id, title, description, created_at)
VALUES (1, "DEFAULT", 'Quiz', 'Default quiz for random quiz session', NOW());

INSERT INTO questions (id, quiz_id, question_text, question_type, created_at)
VALUES (1, 1, 'What is the capital of France?', 'MULTIPLE_CHOICE', NOW()),
       (2, 1, 'The Earth is flat.', 'TRUE_FALSE', NOW()),
       (3, 1, 'Who wrote "Romeo and Juliet"?', 'SHORT_ANSWER', NOW()),
       (4, 1, 'What is the chemical symbol for water?', 'SHORT_ANSWER', NOW()),
       (5, 1, 'What planet is known as the Red Planet?', 'MULTIPLE_CHOICE', NOW()),
       (6, 1, 'Photosynthesis occurs in which part of the plant?', 'MULTIPLE_CHOICE', NOW()),
       (7, 1, 'Who was the first President of the United States?', 'SHORT_ANSWER', NOW()),
       (8, 1, 'The Titanic sank in 1912.', 'TRUE_FALSE', NOW()),
       (9, 1, 'Which ancient civilization built the pyramids?', 'MULTIPLE_CHOICE', NOW());

INSERT INTO options (question_id, option_text, is_correct, created_at)
VALUES (1, 'Berlin', FALSE, NOW()),
       (1, 'Madrid', FALSE, NOW()),
       (1, 'Paris', TRUE, NOW()),
       (1, 'Rome', FALSE, NOW()),
       (2, 'True', TRUE, NOW()),
       (2, 'False', FALSE, NOW()),
       (3, 'William Shakespeare', TRUE, NOW()),
       (3, 'Charles Dickens', FALSE, NOW()),
       (3, 'Mark Twain', FALSE, NOW()),
       (3, 'Ernest Hemingway', FALSE, NOW()),
       (4, 'H2O', TRUE, NOW()),
       (5, 'Earth', FALSE, NOW()),
       (5, 'Mars', TRUE, NOW()),
       (5, 'Jupiter', FALSE, NOW()),
       (5, 'Venus', FALSE, NOW()),
       (6, 'Roots', FALSE, NOW()),
       (6, 'Stems', FALSE, NOW()),
       (6, 'Leaves', TRUE, NOW()),
       (6, 'Flowers', FALSE, NOW()),
       (7, 'George Washington', TRUE, NOW()),
       (8, 'True', TRUE, NOW()),
       (8, 'False', FALSE, NOW()),
       (9, 'Romans', FALSE, NOW()),
       (9, 'Greeks', FALSE, NOW()),
       (9, 'Egyptians', TRUE, NOW()),
       (9, 'Mayans', FALSE, NOW());
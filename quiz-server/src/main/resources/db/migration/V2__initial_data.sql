INSERT INTO quizzes (id, reference_id, title, description, created_at)
VALUES (1, "DEFAULT", 'Quiz', 'Default quiz for random quiz session', NOW());

INSERT INTO questions (id, quiz_id, question_text, question_type, created_at)
VALUES (1, 1, 'What is the capital of France?', 'multiple_choice', NOW()),
       (2, 1, 'The Earth is flat.', 'true_false', NOW()),
       (3, 1, 'Who wrote "Romeo and Juliet"?', 'short_answer', NOW()),
       (4, 1, 'What is the chemical symbol for water?', 'short_answer', NOW()),
       (5, 1, 'What planet is known as the Red Planet?', 'multiple_choice', NOW()),
       (6, 1, 'Photosynthesis occurs in which part of the plant?', 'multiple_choice', NOW()),
       (7, 1, 'Who was the first President of the United States?', 'short_answer', NOW()),
       (8, 1, 'The Titanic sank in 1912.', 'true_false', NOW()),
       (9, 1, 'Which ancient civilization built the pyramids?', 'multiple_choice', NOW());

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
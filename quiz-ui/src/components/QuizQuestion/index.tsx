import React, { useEffect, useState } from 'react';

interface QuizQuestionProps {
    question: string;
    options: string[];
    timeLimit: number;
    onTimeUp: () => void;
}

const QuizQuestion: React.FC<QuizQuestionProps> = ({ question, options, timeLimit, onTimeUp }) => {
    const [timeLeft, setTimeLeft] = useState(timeLimit);

    useEffect(() => {
        if (timeLeft <= 0) {
            onTimeUp();
            return;
        }

        const timer = setInterval(() => {
            setTimeLeft((prevTime) => prevTime - 1);
        }, 1000);

        return () => clearInterval(timer);
    }, [timeLeft, onTimeUp]);

    return (
        <div className="quiz-question-container">
            <h2 className="quiz-question">{question}</h2>
            <div className="quiz-options">
                {options.map((option, index) => (
                    <button key={index} className="quiz-option-button">
                        {option}
                    </button>
                ))}
            </div>
            <div className="quiz-timer">
                Time left: {timeLeft} seconds
            </div>
        </div>
    );
};

export default QuizQuestion;
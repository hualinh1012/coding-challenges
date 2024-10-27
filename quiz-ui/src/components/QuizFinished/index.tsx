import React from 'react';
import { useNavigate } from 'react-router-dom';
import './index.css'; // Import your CSS styles

const QuizFinished: React.FC = () => {
    const navigate = useNavigate();

    const handleRestartClick = () => {
        navigate('/lobby');
    };

    const handleSignInClick = () => {
        navigate('/');
    };

    return (
        <div className="quiz-finished-container">
            <h2 className="quiz-finished-message">Quiz Finished!</h2>
            <p className="quiz-finished-instructions">Thank you for participating!</p>
            <div className="quiz-finished-buttons">
                <button onClick={handleRestartClick} className="quiz-finished-button">Restart quiz</button>
                <button onClick={handleSignInClick} className="quiz-finished-button">Join another quiz</button>
            </div>
        </div>
    );
};

export default QuizFinished;
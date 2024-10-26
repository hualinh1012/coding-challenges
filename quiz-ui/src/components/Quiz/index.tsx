import React from 'react';
import QuizQuestion from '../QuizQuestion';
import LeaderBoard from '../LeaderBoard';
import './index.css';

const Quiz: React.FC = () => {
    const question = "What is the capital of France?";
    const options = ["Berlin", "Madrid", "Paris", "Rome"];
    const leaderboard = [
        {
            name: "linh",
            score: 1
        }
    ]
    const handleTimeUp = () => {
        // alert("Time's up!");
    };

    return (
        <div className="quiz-container">
            <div className="quiz-section">
                <QuizQuestion
                    question={question}
                    options={options}
                    timeLimit={10}
                    onTimeUp={handleTimeUp}
                />
            </div>
            <div className="leaderboard-section">
                <LeaderBoard leaderboard={leaderboard}/>
            </div>
        </div>
    );
};

export default Quiz;
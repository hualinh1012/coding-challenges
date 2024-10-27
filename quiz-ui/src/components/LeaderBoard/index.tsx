import React from 'react';
import './index.css';
import {UserScores} from "../../types/userScores";

const Leaderboard: React.FC<UserScores> = ({leaderboard}) => {
    const scores = leaderboard ? leaderboard : [];
    return (
        <div>
            <h2 className="leaderboard-title">Leaderboard</h2>
            <ul className="leaderboard">
                {scores.map((userScore, index) => (
                    <li key={index} className="leaderboard-item">
                        {userScore.userName}: {userScore.score} points
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Leaderboard;
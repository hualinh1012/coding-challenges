import React from 'react';
import './index.css';

interface LeaderboardProps {
    leaderboard: { name: string; score: number }[];
}

const Leaderboard: React.FC<LeaderboardProps> = ({ leaderboard }) => {
    return (
        <div>
            <h2 className="leaderboard-title">Leaderboard</h2>
            <ul className="leaderboard">
                {leaderboard.map((participant, index) => (
                    <li key={index} className="leaderboard-item">
                        {participant.name}: {participant.score} points
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Leaderboard;
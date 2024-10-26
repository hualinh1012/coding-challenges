import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { getQuiz } from '../../services/getQuizService';
import './index.css';

const LobbyPage: React.FC = () => {
    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [participants, setParticipants] = useState<string[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchParticipants = async () => {
            try {
                const quizId = sessionStorage.getItem('quizId')
                const userName = sessionStorage.getItem('userName')
                if (!quizId || !userName) {
                    navigate('/');
                }

                const quiz = await getQuiz(quizId!);
                setTitle(quiz.title); // Adjust based on your API response
                setDescription(quiz.description); // Adjust based on your API response
                setParticipants(quiz.participants); // Adjust based on your API response
            } catch (error) {
                console.error('Error fetching participants:', error);
            }
        };

        fetchParticipants();
    }, [navigate]);

    return (
        <div className="lobby-container">
            <h2 className="lobby-title">{title}</h2>
            <h2 className="lobby-description">{description}</h2>
            <h3 className="waiting-message">Waiting for other people to join...</h3>
            <h3 className="start-message">Or press here to start</h3>
            <button className="start-button">START</button>
            <h3 className="participants-count">Current Participants: {participants.length}</h3>
            <ul className="participants-list">
                {participants.map((participant, index) => (
                    <li key={index} className="participant-item">{participant}</li>
                ))}
            </ul>
        </div>
    );
};

export default LobbyPage;
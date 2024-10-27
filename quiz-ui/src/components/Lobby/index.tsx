import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {getQuiz, startQuiz} from '../../services/quizService';
import {subscribeRealtimeEvent} from '../../services/realtimeService';
import {useWebsocket} from '../../provider/websocketProvider';
import './index.css';

const Lobby: React.FC = () => {
    const [title, setTitle] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const [participants, setParticipants] = useState<string[]>([]);
    const navigate = useNavigate();
    const websocketService = useWebsocket();

    const listenEvent = (message: any) => {
        if (message.type === 'new-participant-joined') {
            setParticipants(message.payload.participants);
        }
    }

    useEffect(() => {
        const fetchParticipants = async () => {
            try {
                const quizId = sessionStorage.getItem('quizId')
                const userName = sessionStorage.getItem('userName')
                if (!quizId || !userName) {
                    navigate('/');
                }

                const quiz = await getQuiz(quizId!);
                if (!quiz.status) {
                    navigate('/');
                }

                setTitle(quiz.title);
                setDescription(quiz.description);
                setParticipants(quiz.participants);

                subscribeRealtimeEvent(websocketService, quizId!, userName!, listenEvent);

            } catch (error) {
                console.error('Error fetching participants:', error);
            }
        };

        fetchParticipants();
    }, [navigate, websocketService]);

    const handleStartClick = async () => {
        const quizId = sessionStorage.getItem('quizId')
        const isStarted = await startQuiz(quizId!);
        if (isStarted) {
            navigate('/quiz');
        } else {
            alert('Something is wrong, please try again');
        }
    };

    return (
        <div className="lobby-container">
            <h2 className="lobby-title">{title}</h2>
            <h2 className="lobby-description">{description}</h2>
            <h3 className="waiting-message">Waiting for other people to join...</h3>
            <h3 className="start-message">Or press here to start</h3>
            <button className="start-button" onClick={handleStartClick}>START</button>
            <h3 className="participants-count">Current Participants: {participants.length}</h3>
            <ul className="participants-list">
                {participants.map((participant, index) => (
                    <li key={index} className="participant-item">{participant}</li>
                ))}
            </ul>
        </div>
    );
};

export default Lobby;
import React, {useEffect, useState, useCallback} from 'react';
import QuizQuestion from '../QuizQuestion';
import LeaderBoard from '../LeaderBoard';
import {getQuiz, getQuestion, answerQuestion} from "../../services/quizService";
import {useNavigate} from "react-router-dom";
import './index.css';
import {Question} from "../../types/question";
import {AnswerResult} from "../../types/answer";
import QuizFinished from "../QuizFinished";
import {useWebsocket} from "../../provider/websocketProvider";
import {getLeaderBoard} from "../../services/leaderboardService";
import {UserScores} from "../../types/userScores";
import {subscribeRealtimeEvent} from "../../services/realtimeService";

const Quiz: React.FC = () => {
    const navigate = useNavigate();
    const [question, setQuestion] = useState<Question>();
    const [result, setResult] = useState<AnswerResult | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [finish, setFinish] = useState<boolean>(false);
    const [board, setBoard] = useState<UserScores>({leaderboard: []})
    const websocketService = useWebsocket();

    const handleAnswer = async (optionId: number) => {
        const quizId = sessionStorage.getItem('quizId');
        const userName = sessionStorage.getItem('userName');
        const result = await answerQuestion(quizId!, {
            userName: userName!,
            questionId: question!.questionId,
            optionId: optionId
        });

        setResult(result);
        if (result.isCorrect) {
            setMessage("Congratulations!");
        } else {
            setMessage("Oops! Try again next time.");
        }
    };

    const handleTimeUp = async () => {
        if (!result) {
            await handleAnswer(-1);
        }
        setResult(null);
        setMessage(null);
        await fetchNextQuestion();
        await fetchLeaderBoard();
    };

    const fetchNextQuestion = useCallback(async () => {
        try {
            const quizId = sessionStorage.getItem('quizId');
            const userName = sessionStorage.getItem('userName');
            if (!quizId || !userName) {
                navigate('/');
                return;
            }
            const quiz = await getQuiz(quizId!);
            if (!quiz.status) {
                navigate('/');
                return;
            } else if (quiz.status === 'WAITING') {
                navigate('/lobby');
                return;
            } else if (quiz.status === 'FINISHED') {
                setFinish(true);
            }

            const questionData = await getQuestion(quizId!);
            if (questionData && questionData.isFinished) {
                setFinish(true);
            } else {
                setQuestion(questionData);
            }
        } catch (e) {
            console.log(e);
            navigate('/');
        }
    }, [navigate]);

    const fetchLeaderBoard = useCallback(async () => {
        const quizId = sessionStorage.getItem('quizId');
        if (quizId) {
            const board = await getLeaderBoard(quizId);
            setBoard(board);
        }
    }, []);

    const listenEvent = (message: any) => {
        if (message.type === 'leader-board-changed') {
            setBoard(message.payload.leaderBoard);
        }
    }

    const subscribeForLeaderBoardChange = useCallback(async () => {
        const quizId = sessionStorage.getItem('quizId');
        const userName = sessionStorage.getItem('userName');
        subscribeRealtimeEvent(websocketService, quizId!, userName!, listenEvent);
    }, [websocketService]);

    useEffect(() => {
        fetchNextQuestion();
        fetchLeaderBoard();
        subscribeForLeaderBoardChange();
    }, [fetchNextQuestion, fetchLeaderBoard, subscribeForLeaderBoardChange]);

    if (question || finish) {
        return (
            <div className="quiz-container">
                <div className="quiz-section">
                    {finish ?
                        <QuizFinished winner={board.leaderboard[0].userName}/>
                        :
                        <QuizQuestion
                            question={question!}
                            result={result!}
                            message={message}
                            onAnswer={handleAnswer}
                            onTimeUp={handleTimeUp}
                        />}

                </div>
                <div className="leaderboard-section">
                    <LeaderBoard leaderboard={board.leaderboard}/>
                </div>
            </div>
        );
    } else {
        return (
            <div className="quiz-container">
            </div>
        );
    }
};

export default Quiz;
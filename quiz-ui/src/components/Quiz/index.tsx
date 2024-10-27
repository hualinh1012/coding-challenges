import React, {useEffect, useState, useCallback} from 'react';
import QuizQuestion from '../QuizQuestion';
import LeaderBoard from '../LeaderBoard';
import {getQuiz, getQuestion, answerQuestion} from "../../services/quizService";
import {useNavigate} from "react-router-dom";
import './index.css';
import {Question} from "../../types/question";
import {AnswerResult} from "../../types/answer";
import QuizFinished from "../QuizFinished";

const Quiz: React.FC = () => {
    const navigate = useNavigate();
    const [question, setQuestion] = useState<Question>();
    const [result, setResult] = useState<AnswerResult | null>(null);
    const [message, setMessage] = useState<string | null>(null);
    const [finish, setFinish] = useState<boolean>(false);

    const leaderboard = [
        {
            name: "linh",
            score: 1
        }
    ];

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
        await fetchNextQuestion(); // Ensure to await fetching the next question
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

    useEffect(() => {
        fetchNextQuestion();
    }, [fetchNextQuestion]);

    if (question) {
        return (
            <div className="quiz-container">
                <div className="quiz-section">
                    {finish ? <QuizFinished/> : <QuizQuestion
                        question={question!}
                        result={result!}
                        message={message}
                        onAnswer={handleAnswer}
                        onTimeUp={handleTimeUp}
                    />}

                </div>
                <div className="leaderboard-section">
                    <LeaderBoard leaderboard={leaderboard}/>
                </div>
            </div>
        );
    } else {
        return (
            <div className="quiz-container">
                {/* Optional loading state or message could be placed here */}
            </div>
        );
    }
};

export default Quiz;
import React, {useEffect, useState} from 'react';
import QuizQuestion from '../QuizQuestion';
import LeaderBoard from '../LeaderBoard';
import {getQuiz, nextQuestion, answerQuestion} from "../../services/quizService";
import {useNavigate} from "react-router-dom";
import './index.css';
import {Question} from "../../types/question";
import {AnswerResult} from "../../types/answer";

const Quiz: React.FC = () => {
    const navigate = useNavigate();
    const [question, setQuestion] = useState<Question>();
    const [result, setResult] = useState<AnswerResult | null>(null);
    const [message, setMessage] = useState<string | null>(null);

    const leaderboard = [
        {
            name: "linh",
            score: 1
        }
    ]

    const handleAnswer = async (optionId: number) => {
        const quizId = sessionStorage.getItem('quizId');
        const userName = sessionStorage.getItem('userName');
        const result = await answerQuestion(quizId!, {
            userName: userName!,
            questionId: question!.questionId,
            optionId: optionId
        })

        setResult(result);
        if (result.isCorrect) {
            setMessage("Congratulation!")
        } else {
            setMessage("Oops! Try again next time.")
        }
    };

    const handleTimeUp = async () => {
        console.log("trigger time up!")
        setResult(null);
        setMessage(null);
        fetchNextQuestion();
    };

    const fetchNextQuestion = async () => {
        try {
            const quizId = sessionStorage.getItem('quizId');
            const userName = sessionStorage.getItem('userName');
            if (!quizId || !userName) {
                navigate('/');
            }
            const quiz = await getQuiz(quizId!);
            if (!quiz.status) {
                navigate('/');
            } else if (quiz.status && quiz.status === 'WAITING') {
                navigate('/lobby')
            }

            const question = await nextQuestion(quizId!);
            setQuestion(question);
        } catch (e) {
            console.log(e);
            navigate('/');
        }
    }

    useEffect(() => {
        fetchNextQuestion();
    }, [navigate]);

    if (question) {
        return (
            <div className="quiz-container">
                <div className="quiz-section">
                    <QuizQuestion
                        question={question!}
                        result={result!}
                        message={message}
                        onAnswer={handleAnswer}
                        onTimeUp={handleTimeUp}
                    />
                </div>
                <div className="leaderboard-section">
                    <LeaderBoard leaderboard={leaderboard}/>
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
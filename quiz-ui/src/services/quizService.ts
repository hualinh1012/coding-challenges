import {Quiz} from "../types/quiz";
import {Question} from "../types/question";
import {Answer, AnswerResult} from "../types/answer";

export const getQuiz = async (quizId: string): Promise<Quiz> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as Quiz;
};

export const startQuiz = async (quizId: string): Promise<boolean> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}/start`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    return response.ok;
};

export const nextQuestion = async (quizId: string): Promise<Question> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}/question/next`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as Question;
};

export const answerQuestion = async (quizId: string, answer: Answer): Promise<AnswerResult> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}/question/answer`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(answer)
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as AnswerResult;
};
import {UserScores} from "../types/userScores";

export interface SignInRequest {
    userName: string;
    quizId: string;
}

export const getLeaderBoard = async (quizId: string): Promise<UserScores> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}/leaderboard`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as UserScores;
};
export interface QuizResponse {
    quizId: string;
    title: string;
    description: string;
    status: string;
    participants: string[];
}

export const getQuiz = async (quizId: string): Promise<QuizResponse> => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/quiz/${quizId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as QuizResponse;
};
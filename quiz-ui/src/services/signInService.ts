export interface SignInRequest {
    userName: string;
    quizId: string;
}

export const signIn = async (data: SignInRequest): Promise<void> => {
    const response = await fetch(process.env.REACT_APP_API_URL + "/quiz/sign-in", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }
};
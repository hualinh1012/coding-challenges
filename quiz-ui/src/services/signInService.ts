export interface SignInRequest {
    userName: string;
    quizId: string;
}

export interface SignInResponse {
    quizId: string;
    title: string;
    description: string;
    status: string;
}

const apiUrl = process.env.REACT_APP_API_URL + "/quiz";

export const signIn = async (data: SignInRequest): Promise<any> => {
    console.log(apiUrl)
    const response = await fetch(apiUrl + "/sign-in", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error((await response.json()).message);
    }

    return (await response.json()) as SignInResponse;
};
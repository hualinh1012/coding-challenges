export interface Answer {
    userName: string;
    questionId: number;
    optionId: number;
}

export interface AnswerResult {
    questionId: number;
    correctOptionId: number;
    isCorrect: boolean;
}
export interface Question {
    quizId: number;
    questionId: number;
    questionType: string;
    questionText: string;
    options: Option[];
    duration: number;
    isFinished: boolean;
}

export interface Option {
    optionId: number;
    optionText: string;
}
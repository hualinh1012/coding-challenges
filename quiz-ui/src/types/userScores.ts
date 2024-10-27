export interface UserScores {
    leaderboard: Score[];
}

export interface Score {
    userName: string;
    score: number;
}
export interface SubscriptionEvent {
    type: 'subscribe' | 'unsubscribe';
    quizId: string;
    userName: string;
}
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { signIn, SignInRequest } from '../../services/signInService';
import './index.css';

const SignIn: React.FC = () => {
    const [userName, setUserName] = useState<string>('');
    const [quizId, setQuizId] = useState<string>('');
    const [error, setError] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false);
    const navigate = useNavigate();

    const onSignIn = async (data: SignInRequest) => {
        try {
            setLoading(true);
            await signIn({ userName, quizId });

            sessionStorage.setItem('userName', userName);
            sessionStorage.setItem('quizId', quizId);

            navigate('/lobby');
        } catch (error: any) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleSignIn = (e: React.FormEvent) => {
        e.preventDefault();

        if (!userName || !quizId) {
            setError('Both fields are required');
            return;
        }

        setError('');

        onSignIn({ userName, quizId });
    };

    return (
        <div className="container">
            <h2>Sign In to Quiz</h2>
            <form onSubmit={handleSignIn} className="form">
                <div className="inputGroup">
                    <label>Username</label>
                    <input
                        type="text"
                        value={userName}
                        onChange={(e) => setUserName(e.target.value)}
                        className="input"
                        placeholder="Enter your username"
                    />
                </div>
                <div className="inputGroup">
                    <label>Quiz Session ID</label>
                    <input
                        type="text"
                        value={quizId}
                        onChange={(e) => setQuizId(e.target.value)}
                        className="input"
                        placeholder="Enter quiz session ID"
                    />
                </div>
                {error && <p className="error">{error}</p>}
                <button type="submit" className="button" disabled={loading}>
                    {loading ? 'Signing In...' : 'Join Quiz'}
                </button>
            </form>
        </div>
    );
};

export default SignIn;
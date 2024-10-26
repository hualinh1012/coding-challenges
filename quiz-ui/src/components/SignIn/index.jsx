import React, { useState } from 'react';
import './index.css';

const SignInPage = () => {
    const [username, setUsername] = useState('');
    const [quizSessionId, setQuizSessionId] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const onSignIn = async (data) => {
        try {
            setLoading(true);
            const response = await fetch('https://your-api-url.com/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error('Sign In failed!');
            }

            const result = await response.json();
            console.log('Sign In Successful:', result);
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    const handleSignIn = (e) => {
        e.preventDefault();

        if (!username || !quizSessionId) {
            setError('Both fields are required');
            return;
        }

        setError('');

        onSignIn({ username, quizSessionId });
    };


    return (
        <div className="container">
            <h2>Sign In to Quiz</h2>
            <form onSubmit={handleSignIn} className="form">
                <div className="inputGroup">
                    <label>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="input"
                        placeholder="Enter your username"
                    />
                </div>
                <div className="inputGroup">
                    <label>Quiz Session ID</label>
                    <input
                        type="text"
                        value={quizSessionId}
                        onChange={(e) => setQuizSessionId(e.target.value)}
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

export default SignInPage;
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignInPage from './components/SignIn';
import LobbyPage from './components/Lobby';

const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<SignInPage />} />
                <Route path="/lobby" element={<LobbyPage />} />
            </Routes>
        </Router>
    );
};

export default App;
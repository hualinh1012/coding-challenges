import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import SignIn from './components/SignIn';
import Lobby from './components/Lobby';
import Quiz from './components/Quiz';
import {WebsocketProvider} from './provider/websocketProvider';

const App: React.FC = () => {
    return (
        <WebsocketProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<SignIn/>}/>
                    <Route path="/lobby" element={<Lobby/>}/>
                    <Route path="/quiz" element={<Quiz/>}/>
                </Routes>
            </Router>
        </WebsocketProvider>

    );
};

export default App;
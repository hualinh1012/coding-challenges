import React, {useEffect, useState} from 'react';

interface Participant {
    username: string;
}

const LobbyPage: React.FC = () => {
    const [participants, setParticipants] = useState<Participant[]>([]);

    useEffect(() => {
        const fetchParticipants = async () => {
            try {
                const response = await fetch('https://your-api-url.com/lobby');
                if (!response.ok) {
                    throw new Error('Failed to fetch participants');
                }

                const data = await response.json();
                setParticipants(data.participants); // Adjust based on your API response
            } catch (error) {
                console.error('Error fetching participants:', error);
            }
        };

        fetchParticipants();
    }, []);

    return (
        <div>
            <h2>Lobby</h2>
            <h3>Current Participants: {participants.length}</h3>
            <ul>
                {participants.map((participant, index) => (
                    <li key={index}>{participant.username}</li>
                ))}
            </ul>
        </div>
    );
};

export default LobbyPage;
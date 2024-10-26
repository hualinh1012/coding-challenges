import {createContext, useContext, useEffect, useState} from 'react';

interface WebsocketServiceInterface {
    connect(url: string): void;

    sendMessage(data: any): void;

    onMessage(callback: (data: any) => void): void;

    close(): void;
}

class WebsocketService implements WebsocketServiceInterface {
    private socket: WebSocket | null = null;
    private messageCallback: (data: any) => void = () => {
    };

    connect(url: string): void {
        this.socket = new WebSocket(url);

        this.socket.onopen = () => {
            console.log('WebSocket connection established');
        };

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data as string);
            this.messageCallback(data);
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };

        this.socket.onclose = () => {
            console.log('WebSocket connection closed');
        };
    }

    sendMessage(data: any): void {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(JSON.stringify(data));
        }
    }

    onMessage(callback: (data: any) => void): void {
        this.messageCallback = callback;
    }

    close(): void {
        if (this.socket) {
            this.socket.close();
        }
    }
}

const WebsocketContext = createContext<WebsocketService | null>(null);

export const WebsocketProvider: React.FC<{ children: React.ReactNode }> = ({children}) => {
    const [websocketService] = useState(() => new WebsocketService());

    useEffect(() => {
        websocketService.connect(process.env.REACT_APP_WEBSOCKET_URL || 'ws://localhost:3030');

        return () => {
            websocketService.close();
        };
    }, [websocketService]);

    return (
        <WebsocketContext.Provider value={websocketService}>
            {children}
        </WebsocketContext.Provider>
    );
};

export const useWebsocket = (): WebsocketService => {
    const context = useContext(WebsocketContext);
    if (!context) {
        throw new Error('useWebsocket must be used within a WebsocketProvider');
    }
    return context;
};
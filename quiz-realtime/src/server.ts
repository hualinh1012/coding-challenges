import express from 'express';
import { Kafka } from 'kafkajs';
import { WebSocketServer } from 'ws';

const app = express();
const PORT = process.env.PORT || 3000;

// Initialize Kafka
const kafka = new Kafka({
    clientId: 'kafka-consumer-app',
    brokers: ['localhost:9092'], // Update with your Kafka broker addresses
});

const consumer = kafka.consumer({ groupId: 'test-group' });

// Create WebSocket server
const wss = new WebSocketServer({ noServer: true });

// Handle WebSocket connections
wss.on('connection', (ws) => {
    console.log('New client connected');

    // Handle messages received from the client
    ws.on('message', (message) => {
        console.log(`Received message from client: ${message}`);
        // You can add logic here to respond to the client if needed
    });

    ws.on('close', () => {
        console.log('Client disconnected');
    });
});

const run = async () => {
    // Connect the consumer
    await consumer.connect();
    console.log('Connected to Kafka');

    // Subscribe to the topic
    await consumer.subscribe({ topic: 'your-topic-name', fromBeginning: true });

    // Run the consumer
    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            console.log(`Received message: ${message.value?.toString()} from topic: ${topic}`);

            // Broadcast the message to all connected WebSocket clients
            // wss.clients.forEach((client) => {
            //     if (client.readyState === client.OPEN) {
            //         client.send(message.value?.toString());
            //     }
            // });
        },
    });
};

// Start the consumer
run().catch(console.error);

// Create a server to integrate WebSocket with the Express app
const server = app.listen(PORT, () => {
    console.log(`HTTP Server is running on http://localhost:${PORT}`);
});

// Upgrade the server to handle WebSocket connections
server.on('upgrade', (request, socket, head) => {
    wss.handleUpgrade(request, socket, head, (ws) => {
        wss.emit('connection', ws, request);
    });
});

// Set up a simple Express server
app.get('/', (req, res) => {
    res.send('Kafka Consumer Server is running');
});
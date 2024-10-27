# Getting Started

## Prerequisites

Before you begin, make sure you have the following tools installed:

- **Java 21**: [Download Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or install via a package manager (e.g., SDKMAN, Homebrew).
- **Node.js** (version 20 or higher): [Download Node.js](https://nodejs.org/).
- **Docker**: Install [Docker Desktop](https://www.docker.com/).

To verify installations, use these commands:

```bash
java -version
node -v
```

## Step to set up

### Environment

Use docker-compose to bring the environment up. It contains MySQL, Redis, Kafka
```bash
cd dev-environment
docker-compose up -d
```

### Backend setup
Everything is ready inside `application.properties` and `.env`. Just need to install dependencies and start :) 

#### Quiz-server
```bash
cd quiz-server
./mvnw clean install
./mvnw spring-boot:run
```
#### Quiz-realtime
```bash
cd quiz-realtime
npm install
npm start
```

### Frontend setup
Everything is ready inside `.env`

#### Quiz-realtime

```bash
cd quiz-ui
npm install
npm start
```
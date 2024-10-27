# Project Structure

## Table of content
1. [quiz-server](#quiz-server)
2. [quiz-realtime](#quiz-realtime)
2. [quiz-ui](#quiz-ui)

## quiz-server

This Spring Boot project is organized into a series of packages designed to keep code modular, maintainable, and scalable. Each package serves a specific purpose within the application, as outlined below.

This modular structure promotes clean code organization, facilitates testing, and simplifies maintenance, making it easy to locate specific components based on their functionality within the application.

### Packages

#### 1. **Configuration**
- **Path**: `io.elsanow.challenge.configuration`
- **Purpose**: Contains configuration classes for Spring Boot and any other application-specific configurations. This might include configurations for security, data sources, application properties, or other environment-based settings.

#### 2. **Exception**
- **Path**: `io.elsanow.challenge.exception`
- **Purpose**: Handles custom exceptions used within the application. This package includes custom exception classes and centralized error-handling mechanisms, allowing for graceful error responses and logging.

#### 3. **Quiz**
- **Path**: `io.elsanow.challenge.quiz`
- **Purpose**: This is the core package for the quiz-related functionality. It contains multiple sub-packages that separate concerns based on the layers and components of the application.

##### Sub-packages in `Quiz`

- **Adapter**
  - **Path**: `io.elsanow.challenge.quiz.adapter`
  - **Purpose**: Holds adapter classes that bridge the application to external systems or services. This includes any interfaces with third-party APIs, message brokers, or data sources not directly managed by the application.

- **Controller**
  - **Path**: `io.elsanow.challenge.quiz.controller`
  - **Purpose**: Manages REST endpoints for the quiz-related functionalities. This layer is responsible for handling HTTP requests and responses, delegating business logic processing to the service layer.

- **Domain**
  - **Path**: `io.elsanow.challenge.quiz.domain`
  - **Purpose**: This sub-package is the application's domain layer, which encapsulates the core business logic and data structures for the quiz functionality.

  - **bo (Business Objects)**
    - **Path**: `io.elsanow.challenge.quiz.domain.bo`
    - **Purpose**: Contains business objects that represent complex data structures and processes required for the application's business logic.

  - **Entity**
    - **Path**: `io.elsanow.challenge.quiz.domain.entity`
    - **Purpose**: Defines JPA entities, representing database tables for persisting quiz data.

  - **Enumeration**
    - **Path**: `io.elsanow.challenge.quiz.domain.enumeration`
    - **Purpose**: Contains enums representing fixed sets of constants used within the quiz domain, such as quiz statuses, question types, or other defined values.

- **DTO**
  - **Path**: `io.elsanow.challenge.quiz.dto`
  - **Purpose**: This package holds Data Transfer Objects (DTOs) for handling data movement between different layers or services, allowing for a clean separation of concerns.

  - **Request**
    - **Path**: `io.elsanow.challenge.quiz.dto.request`
    - **Purpose**: Contains DTOs for request payloads sent to the application, allowing for validation and data structuring before processing.

  - **Response**
    - **Path**: `io.elsanow.challenge.quiz.dto.response`
    - **Purpose**: Holds response DTOs used to structure the data sent back to clients or other systems.

  - **Event**
    - **Path**: `io.elsanow.challenge.quiz.dto.event`
    - **Purpose**: Contains DTOs related to event-driven architecture, handling event payloads for messages or notifications published within the system.

- **Repository**
  - **Path**: `io.elsanow.challenge.quiz.repository`
  - **Purpose**: Manages Spring Data JPA repositories for accessing and managing quiz data stored in the database.

- **Service**
  - **Path**: `io.elsanow.challenge.quiz.service`
  - **Purpose**: Contains the service layer, where business logic for the quiz functionality is implemented. Services here perform tasks such as data processing, rule enforcement, and transaction management.


## quiz-realtime

The Quiz-Realtime project is designed to handle real-time communication for quiz sessions. It is a simple service that listens to messages from a Kafka topic and sends those messages to connected clients via WebSocket.
There is no complex folder structure or multiple modules; instead, it relies on a single file, server.ts, to manage the core functionality.


## quiz-ui

This frontend project structure is designed for modular and scalable development. The main code resides in the `src` folder, which contains sub-folders for different components, services, types, and providers.

### Project Structure

#### 1. **Components**
- **Path**: `src/components`
- **Purpose**: Contains reusable UI components used throughout the application. Each component has its own folder, which includes:
  - A `.tsx` file for the component logic and JSX.
  - A `.css` (or `.scss`) file for component-specific styling.

#### 2. **Provider**
- **Path**: `src/provider`
  - **Purpose**: Manages context providers for shared state across components. It contains the `WebSocketProvider`, which is used for managing WebSocket connections, handling messages, and sharing the WebSocket state with other components.
  - **Example**:
    ```
    src/provider/websocketProvider.tsx
    ```

#### 3. **Services**
- **Path**: `src/services`
- **Purpose**: Contains files for making API calls and managing data flow between the frontend and backend. Each service is responsible for a specific resource or feature.
- **Example**:
  ```
  src/services/quizService.ts
  ```

#### 4. **Types**
- **Path**: `src/types`
- **Purpose**: Stores TypeScript type definitions for the application. This includes interfaces, types, and enums used across various components and services, promoting type safety and consistency.
- **Example**:
  ```
  src/types/answer.ts


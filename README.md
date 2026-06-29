# LGBT+Tech Events API

Backend API for managing LGBT+Tech community events.

The project is being developed following Domain-Driven Design (DDD) principles using a modular monolith architecture.

The main idea of this project is to eventually replace our use of Meetup.com with this platform.

For future improvements and goals, check out [the backlog](BACKLOG.md).

## Features

_Current functionality includes_

- Create draft events
- List events
- Retrieve events by ID
- Publish draft events
- Validate incoming requests

## Tech Stack

- Kotlin
- Spring Boot 4
- Gradle
- PostgreSQL
- Flyway
- Docker
- JUnit 5
- Testcontainers
- Mockito

## Running the Application

### Start PostgreSQL

`docker compose up -d`

### Run application

`./gradlew bootRun`

The API will be available at:

`http://localhost:8080

### Run tests

`./gradlew test`

### API Endpoints

- `POST /events`

Example request:

```
{
  "title": "LGBT+Tech Barcelona",
  "description": "Monthly meetup",
  "startsAt": "2026-07-01T18:30:00Z",
  "endsAt": "2026-07-01T21:00:00Z",
  "venueName": "Example Venue",
  "venueAddress": "Barcelona",
  "capacity": 50
}
```

- `GET /events`
- `GET /events/{id}`
- `PATCH /events/{id}/publish`
# Chaukas

Chaukas is an uptime and HTTP monitoring service built with Java and Spring Boot.

The project is being developed from scratch with a focus on learning and applying
production-oriented backend engineering practices.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- Maven

## Current Features

- User database schema
- Database migrations with Liquibase
- Development and production configuration profiles
- PostgreSQL integration

## Planned Features

- HTTP/HTTPS monitor management
- Monitor versioning
- Scheduled health checks
- Response time and status tracking
- Incident detection and tracking
- Configurable alert policies
- Email notifications
- Concurrent monitoring
- Redis-based components where required
- Monitoring analytics

## Architecture

The project is being developed incrementally, with the goal of building a
production-oriented backend rather than a simple CRUD application.

## Development

### Requirements

- Java 21
- PostgreSQL
- Maven

### Running Locally

1. Create a PostgreSQL database named `chaukas`.
2. Configure the required environment variables.
3. Activate the `dev` Spring profile.
4. Run the application using Maven or IntelliJ.

Liquibase manages database schema changes and migrations.

## Database Migrations

Database schema changes are managed using Liquibase.

Applied changesets should not be modified. New schema changes should be
introduced through new changesets.

## Project Status

🚧 **Work in Progress**

Chaukas is actively being developed and its architecture and features will
evolve as the project progresses.

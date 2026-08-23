# Chaukas

**Chaukas** is a backend monitoring platform for developers and teams who want to continuously monitor the availability of their applications and APIs.

A user registers a monitor by providing a health-check URL and monitoring configuration. Chaukas periodically checks the endpoint, evaluates the response, tracks failures and incidents, and is designed to notify the owner when a service remains unavailable.

The project is being built with a strong focus on **real-world backend engineering**, including data modeling, transactional consistency, security, scheduling, failure handling, persistence, observability, and scalable system design.

---

## Why Chaukas?

When an application goes down, discovering the problem quickly matters.

Chaukas aims to provide a simple monitoring workflow:

```text
User
 │
 │ Create monitor
 ▼
Chaukas
 │
 │ Periodically check endpoint
 ▼
Application / API
 │
 ├── Healthy ──────► Record successful check
 │
 └── Failing ──────► Track consecutive failures
                         │
                         ▼
                  Failure threshold
                         │
                         ▼
                    Notification
```

The system is designed so that a transient failure does not immediately become a downtime incident. Consecutive failures, thresholds, retries, and notification policies are part of the monitoring domain.

---

## Current Architecture

At the current stage, Chaukas is being developed as a modular Spring Boot backend.

### Core domain

```text
User
 │
 ├── UserCredentials
 │
 └── Monitor
       │
       └── MonitorConfig
             │
             ├── URL
             ├── expected status code
             ├── timeout
             ├── check interval
             ├── failure threshold
             └── reminder interval
```

### Monitor and configuration model

A `Monitor` represents the long-lived monitoring resource.

`MonitorConfig` represents a versioned configuration for that monitor.

This separation allows configuration changes to be tracked without rewriting historical configuration state.

The monitor maintains runtime state such as:

* Current status
* Consecutive failures
* Enabled/disabled state
* Current configuration
* Next scheduled check
* Creation/update timestamps

Configurations contain monitoring-specific settings such as:

* Monitor name
* URL
* Expected HTTP status code
* Request timeout
* Check interval
* Failure threshold
* Reminder interval

---

## Configuration Versioning

Monitor configuration is intentionally separated from the monitor itself.

A monitor can have multiple configurations over its lifetime:

```text
Monitor #42

Version 1
   │
   ├── created
   │
   ▼
Version 2
   │
   ├── configuration changed
   │
   ▼
Version 3
   │
   └── current configuration
```

Each configuration belongs to a monitor and has a version number.

The database enforces uniqueness for:

```text
(monitor_id, version)
```

This gives us a foundation for preserving configuration history instead of overwriting previous configurations.

---

## Monitoring Rules

Chaukas currently models several important monitoring constraints.

### Check interval

The minimum check interval is currently:

```text
60 seconds
```

### Timeout

A monitor timeout must be shorter than its check interval:

```text
timeout < checkInterval
```

This prevents a check from potentially running longer than the interval at which the next check is expected.

### Failure threshold

A notification is not triggered by the first failure.

For example, with:

```text
failureThreshold = 10
```

the service must reach the configured failure threshold before the initial notification is sent.

### Reminder notifications

After the initial threshold is reached, reminders are based on the configured reminder interval.

For example:

```text
failureThreshold       = 10
reminderAfterFailures  = 7
```

Notifications occur at:

```text
10
17
24
31
38
45
...
```

The reminder interval therefore represents the number of additional consecutive failures after the previous notification.

---

## API Scope

The initial monitoring version intentionally keeps the HTTP check model simple.

### V1

Monitors currently target normal HTTP endpoints using a `GET` request.

The initial configuration does not attempt to support every possible API authentication or HTTP request scenario.

Future versions can introduce support for:

* HTTP methods
* Request headers
* Authentication credentials
* Request bodies
* Custom health-check conditions
* More advanced response validation

The goal is to avoid prematurely designing features before their actual requirements are understood.

---

## Data Integrity

Database constraints are treated as part of the application's correctness model rather than relying exclusively on Java validation.

The database currently enforces rules such as:

* Required relationships
* Unique monitor configuration versions
* Valid HTTP status-code ranges
* Positive timeout values
* Positive failure thresholds
* Positive reminder intervals
* Non-negative consecutive failures
* Minimum check interval
* Timeout being smaller than check interval

Application-level validation is also used for incoming API requests.

This provides two layers of protection:

```text
Client
  ↓
DTO validation
  ↓
Service/domain rules
  ↓
Database constraints
```

---

## Authentication

Authentication is currently being designed around a dedicated credentials model.

Users and authentication credentials are separated:

```text
users
  │
  │ 1 : 1
  ▼
user_credentials
```

The credential table stores the password hash rather than a plaintext password.

The planned authentication architecture uses:

* Spring Security
* Password hashing
* JWT access tokens
* Bearer authentication
* Stateless API authentication

Authentication and authorization will be introduced before exposing monitor operations as user-owned resources.

---

## Persistence

### Database

PostgreSQL is used as the primary relational database.

### Database migrations

Liquibase manages database schema evolution.

Schema changes are maintained as incremental migrations rather than relying on Hibernate to create production tables.

Example:

```text
001 - users
002 - monitor
003 - monitor_config
004 - monitor/current-config relationship
005 - monitoring interval constraints
006 - user credentials
...
```

This gives the project experience with real database migration workflows and schema evolution.

---

## Technology Stack

| Technology        | Purpose                                    |
| ----------------- | ------------------------------------------ |
| Java              | Primary programming language               |
| Spring Boot       | Backend application framework              |
| Spring Data JPA   | Persistence layer                          |
| Hibernate         | ORM                                        |
| Spring Validation | API input validation                       |
| Spring Security   | Authentication & authorization             |
| PostgreSQL        | Primary database                           |
| Liquibase         | Database migrations                        |
| Redis             | Caching / distributed backend capabilities |
| Docker            | Containerization                           |
| GitHub Actions    | CI automation                              |
| OpenAPI / Swagger | API documentation                          |

---

## Engineering Principles

Chaukas is being developed around several backend engineering principles.

### Separation of concerns

API, service, persistence, domain, security, and configuration responsibilities are kept separate.

### Database as a consistency boundary

Important invariants are enforced at the database level where appropriate.

### Explicit transactions

Operations that modify multiple related entities are treated as atomic business operations.

### Encapsulation

Entities do not expose setters simply for convenience when a state transition should instead be represented by a meaningful domain operation.

### Versioned configuration

Historical configuration should not be unnecessarily overwritten.

### Defense in depth

Validation exists at multiple layers:

```text
API validation
      ↓
Business rules
      ↓
Database constraints
```

### Incremental complexity

Features are introduced when they solve a real requirement instead of adding distributed-system complexity prematurely.

---

## Planned Monitoring Architecture

The monitoring engine will eventually follow a flow similar to:

```text
Scheduler
    │
    ▼
Find monitors due for checking
    │
    ▼
Dispatch check
    │
    ▼
HTTP request
    │
    ├───────────────┐
    ▼               ▼
Success           Failure
    │               │
    ▼               ▼
Record result   Update failure state
                    │
                    ▼
              Threshold reached?
                 │       │
                No      Yes
                 │       │
                 │       ▼
                 │   Notification
                 │       │
                 │       ▼
                 │   Reminder policy
                 │
                 ▼
             Schedule next check
```

The exact execution model will evolve as scalability requirements become clearer.

Potential areas include:

* Thread pools
* Persistent scheduling
* Queues
* Retry policies
* Distributed locking
* Idempotent processing
* Failure/incident tracking
* Notification workers

---

## Project Status

Chaukas is actively being developed.

The project intentionally evolves from a relatively simple Spring Boot application toward a production-oriented monitoring system. Architectural decisions are made incrementally as new requirements are introduced, with emphasis on understanding the underlying engineering trade-offs.

The objective is not only to build a monitoring product, but to explore and implement the engineering practices used in modern backend systems.

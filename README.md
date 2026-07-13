# PMGSY Scheme Classifier Backend

Enterprise-grade Spring Boot backend for **Intelligent Classification of Rural Infrastructure Projects**. The API predicts PMGSY scheme categories by forwarding features to an **IBM watsonx.ai deployment** (no local `.pkl` model hosting).

## Features

- Layered Spring Boot architecture (controller -> service -> IBM integration service)
- IBM watsonx.ai Deployment REST API integration using `RestClient`
- Request validation with Jakarta Validation + custom business checks
- Centralized error handling with standardized error payloads
- OpenAPI/Swagger UI documentation
- JUnit 5 + Mockito tests
- Docker-ready deployment

## Tech Stack

- Java 17
- Spring Boot 3.2+
- Maven
- Spring Web
- Spring Validation
- Spring Boot Actuator
- Lombok
- Springdoc OpenAPI
- RestClient
- JUnit 5
- Mockito
- Docker

## Project Structure

```text
com.pmgsy.classifier
├── controller
├── service
├── dto
├── entity
├── config
├── exception
├── util
├── constants
├── validation
├── mapper
└── PmgsyApplication
```

## API Endpoints

- `GET /` - application info
- `GET /api/v1/health` - health check
- `GET /api/v1/model-info` - model metadata
- `POST /api/v1/predict` - PMGSY scheme prediction

### `POST /api/v1/predict` sample request

```json
{
  "stateName": "Odisha",
  "districtName": "Khordha",
  "roadWorksSanctioned": 15,
  "roadLengthSanctioned": 12.5,
  "bridgesSanctioned": 2,
  "costOfWorksSanctioned": 5000000,
  "roadWorksCompleted": 12,
  "roadLengthCompleted": 10,
  "bridgesCompleted": 1,
  "expenditureOccured": 4500000,
  "roadWorksBalance": 3,
  "roadLengthBalance": 2.5,
  "bridgesBalance": 1
}
```

### sample response

```json
{
  "prediction": "PMGSY-II",
  "confidence": 0.97,
  "timestamp": "2026-07-12T12:30:00Z"
}
```

## Sensitive Data Management with `.env`

This repository is configured to keep secrets out of Git:

- `.env` contains local sensitive values and is gitignored.
- `.env.example` is committed as a template.
- `.gitignore` excludes `.env`.

### required environment variables

- `APP_SERVER_PORT`
- `IBM_API_KEY`
- `IBM_DEPLOYMENT_ENDPOINT`
- `IBM_DEPLOYMENT_ID`
- `IBM_API_VERSION`
- `IBM_TOKEN_URL`

## Local Setup

1. Copy the template:

```bash
cp .env.example .env
```

On Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

2. Edit `.env` and set your IBM credentials.
3. Run tests:

```bash
mvn test
```

4. Run app:

```bash
mvn spring-boot:run
```

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

## Docker

Build and run with Docker Compose (reads `.env` automatically via `env_file`):

```bash
docker compose up --build
```

## Validation and Error Handling

Validation includes:

- null checks
- empty string checks
- non-negative numeric checks
- request consistency rules (sanctioned/completed/balance)

Global exception handling maps known errors (validation, malformed body, IBM API failures, timeouts, runtime errors) to standardized API responses.

## IBM Cloud Deployment Notes

Suitable for IBM Cloud Code Engine or Cloud Foundry style deployment with environment variable-based configuration.

## Security Notes

- Never commit `.env`.
- Rotate IBM API keys if accidentally exposed.
- Prefer IBM Secrets Manager or platform secret stores in production.

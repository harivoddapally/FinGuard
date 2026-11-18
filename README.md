# FinGuard — Explainable Fraud Detection & Banking Risk Platform

A GitHub-ready full-stack Java portfolio project demonstrating transaction monitoring, explainable fraud-risk scoring, case review, and auditability.

## Why this project
Digital payments create a need for fast, explainable transaction monitoring. FinGuard is a portfolio-grade prototype that scores incoming transactions, explains risk signals, and helps an analyst review suspicious activity. It is **not** connected to a real bank and does not make real financial decisions.

## Stack
- Java 17, Spring Boot 3.3, Spring Web, Spring Data JPA, Bean Validation
- H2 in-memory database by default (easy demo); switch to MySQL in `application.yml`
- Vanilla HTML/CSS/JavaScript dashboard (served by Spring Boot)
- REST API, layered architecture, rule-based explainable risk engine

## Features
- Create and list transactions
- Explainable risk score (0–100) and LOW / MEDIUM / HIGH classification
- Rules for unusual amount, velocity, new device, international location, and repeated attempts
- Dashboard KPIs and transaction filtering
- Analyst review status: OPEN / CONFIRMED_FRAUD / FALSE_POSITIVE
- Audit log for review actions
- Validation and consistent API error responses

## Run locally
Requirements: JDK 17+ and Maven 3.9+.

```bash
cd backend
mvn spring-boot:run
```
Open http://localhost:8080. H2 console: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:finguard`
- Username: `sa`
- Password: (blank)

The app seeds demo transactions on startup.

## MySQL option
Create a database named `finguard`, then change the datasource settings in `backend/src/main/resources/application.yml` (commented example). Add the MySQL driver dependency to `pom.xml` if you use MySQL.

## API
| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/dashboard` | KPI summary |
| GET | `/api/transactions` | List transactions |
| POST | `/api/transactions` | Score and save a transaction |
| PATCH | `/api/transactions/{id}/review` | Update analyst review |
| GET | `/api/audit` | Recent audit events |

Example POST:
```json
{
  "customerId": "CUS-1042",
  "amount": 98500,
  "currency": "INR",
  "merchant": "QuickPay Electronics",
  "country": "IN",
  "deviceId": "DEV-NEW-91",
  "newDevice": true,
  "international": false
}
```

## Demo flow for presentation
1. Open dashboard and review the seeded transactions.
2. Submit a high-value transaction using `newDevice: true`.
3. Show the risk score and triggered explanations.
4. Mark the transaction as `CONFIRMED_FRAUD` or `FALSE_POSITIVE`.
5. Open Audit Log to show traceability.

## Architecture
Browser UI → Spring REST Controller → Service Layer → Risk Engine → JPA Repository → H2/MySQL

## Important engineering notes
- Risk score is a transparent demo heuristic, not a trained machine-learning model.
- Do not use real customer, card, account, or personally identifiable data.
- Production deployment would require authentication/authorization, encryption, secrets management, rate limiting, monitoring, model governance, and formal security testing.

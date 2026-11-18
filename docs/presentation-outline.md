# FinGuard — Presentation Notes

## 1. Problem statement
Digital payments are fast, but suspicious activity can cause financial loss and customer disruption. Teams need a way to prioritize transactions and understand why an alert was raised.

## 2. Proposed solution
FinGuard is a full-stack transaction monitoring prototype. It accepts transaction data, evaluates transparent risk indicators, assigns a score and risk band, and gives an analyst a review workflow with an audit trail.

## 3. Technology
Java 17, Spring Boot, Spring Web, Spring Data JPA, Bean Validation, H2, HTML, CSS, JavaScript, REST APIs.

## 4. Workflow
1. A transaction is submitted from the dashboard.
2. The Spring controller validates the request.
3. The service calls the RiskEngine.
4. Risk rules calculate a score from 0 to 100 and return explanations.
5. JPA persists the transaction and an audit event.
6. Analyst updates review status; the action is recorded.

## 5. Risk rules used in the prototype
- Amount ≥ ₹75,000: +30
- Amount ≥ ₹30,000: +15 (instead of the high-amount rule)
- New device: +25
- International flag: +25
- Country not IN: +15
- Merchant contains “crypto”: +15
Score is capped at 100. LOW: 0–29, MEDIUM: 30–59, HIGH: 60–100.

## 6. What makes it explainable
The UI displays both the numerical score and the exact rules that contributed to it. This is more useful for analyst review than a score with no reason.

## 7. Demo
Show the dashboard, submit a high-value transaction from a new device, inspect the triggered reasons, change its review status, and show the audit event.

## 8. Limitations and future scope
This version uses deterministic demo rules, not a trained AI/ML model. Next steps: authenticated analyst roles, MySQL/PostgreSQL, unit/integration tests, Kafka event ingestion, device/customer behavioural baselines, a trained anomaly model with model monitoring, and deployment with CI/CD.

## 9. Closing
FinGuard demonstrates end-to-end Java application development, REST API design, persistence, business-rule implementation, responsive UI, and auditability in a relevant fintech use case.

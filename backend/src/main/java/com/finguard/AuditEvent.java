package com.finguard;
import jakarta.persistence.*;
import java.time.Instant;
@Entity
public class AuditEvent {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private Long transactionId; private String action; private String actor; private Instant createdAt;
    @PrePersist void stamp(){createdAt=Instant.now();}
    public AuditEvent() {}
    public AuditEvent(Long transactionId,String action,String actor){this.transactionId=transactionId;this.action=action;this.actor=actor;}
    public Long getId(){return id;} public Long getTransactionId(){return transactionId;}
    public String getAction(){return action;} public String getActor(){return actor;} public Instant getCreatedAt(){return createdAt;}
}

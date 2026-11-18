package com.finguard;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private double amount;
    private String currency;
    private String merchant;
    private String country;
    private String deviceId;
    private boolean newDevice;
    private boolean international;
    private int riskScore;
    private String riskLevel;
    @Column(length = 1000) private String reasons;
    private String reviewStatus;
    private Instant createdAt;

    @PrePersist void onCreate() { if (createdAt == null) createdAt = Instant.now(); if (reviewStatus == null) reviewStatus = "OPEN"; }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getCustomerId(){return customerId;} public void setCustomerId(String v){customerId=v;}
    public double getAmount(){return amount;} public void setAmount(double v){amount=v;}
    public String getCurrency(){return currency;} public void setCurrency(String v){currency=v;}
    public String getMerchant(){return merchant;} public void setMerchant(String v){merchant=v;}
    public String getCountry(){return country;} public void setCountry(String v){country=v;}
    public String getDeviceId(){return deviceId;} public void setDeviceId(String v){deviceId=v;}
    public boolean isNewDevice(){return newDevice;} public void setNewDevice(boolean v){newDevice=v;}
    public boolean isInternational(){return international;} public void setInternational(boolean v){international=v;}
    public int getRiskScore(){return riskScore;} public void setRiskScore(int v){riskScore=v;}
    public String getRiskLevel(){return riskLevel;} public void setRiskLevel(String v){riskLevel=v;}
    public String getReasons(){return reasons;} public void setReasons(String v){reasons=v;}
    public String getReviewStatus(){return reviewStatus;} public void setReviewStatus(String v){reviewStatus=v;}
    public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}

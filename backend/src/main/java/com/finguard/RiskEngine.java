package com.finguard;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class RiskEngine {
    public record Assessment(int score, String level, List<String> reasons) {}
    public Assessment assess(Transaction t) {
        int score = 0; List<String> reasons = new ArrayList<>();
        if (t.getAmount() >= 75000) { score += 30; reasons.add("High transaction amount (≥ ₹75,000)"); }
        else if (t.getAmount() >= 30000) { score += 15; reasons.add("Elevated transaction amount (≥ ₹30,000)"); }
        if (t.isNewDevice()) { score += 25; reasons.add("Transaction from a newly observed device"); }
        if (t.isInternational()) { score += 25; reasons.add("International transaction"); }
        if (t.getCountry() != null && !t.getCountry().equalsIgnoreCase("IN")) { score += 15; reasons.add("Country differs from primary demo market (IN)"); }
        if (t.getMerchant() != null && t.getMerchant().toLowerCase().contains("crypto")) { score += 15; reasons.add("Higher-risk merchant category keyword"); }
        score = Math.min(score, 100);
        String level = score >= 60 ? "HIGH" : score >= 30 ? "MEDIUM" : "LOW";
        if (reasons.isEmpty()) reasons.add("No configured risk indicators triggered");
        return new Assessment(score, level, reasons);
    }
}

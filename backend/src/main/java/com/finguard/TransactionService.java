package com.finguard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactions; private final AuditRepository audits; private final RiskEngine engine;
    public TransactionService(TransactionRepository t, AuditRepository a, RiskEngine e){transactions=t;audits=a;engine=e;}
    public List<Transaction> all(){return transactions.findTop100ByOrderByCreatedAtDesc();}
    @Transactional public Transaction create(Transaction t){
        RiskEngine.Assessment a=engine.assess(t); t.setRiskScore(a.score()); t.setRiskLevel(a.level());
        t.setReasons(String.join(" • ",a.reasons())); Transaction saved=transactions.save(t);
        audits.save(new AuditEvent(saved.getId(),"TRANSACTION_SCORED: "+a.level()+" ("+a.score()+")","SYSTEM")); return saved;
    }
    @Transactional public Transaction review(Long id,String status){
        if(!List.of("OPEN","CONFIRMED_FRAUD","FALSE_POSITIVE").contains(status)) throw new IllegalArgumentException("Invalid review status");
        Transaction t=transactions.findById(id).orElseThrow(()->new IllegalArgumentException("Transaction not found"));
        t.setReviewStatus(status); Transaction saved=transactions.save(t);
        audits.save(new AuditEvent(id,"REVIEW_UPDATED: "+status,"ANALYST")); return saved;
    }
    public Dashboard dashboard(){
        List<Transaction> all=all(); long high=all.stream().filter(t->"HIGH".equals(t.getRiskLevel())).count();
        long medium=all.stream().filter(t->"MEDIUM".equals(t.getRiskLevel())).count();
        double volume=all.stream().mapToDouble(Transaction::getAmount).sum();
        return new Dashboard(all.size(),high,medium,volume);
    }
    public List<AuditEvent> audit(){return audits.findTop50ByOrderByCreatedAtDesc();}
    public record Dashboard(long totalTransactions,long highRisk,long mediumRisk,double monitoredVolume){}
}

package com.finguard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RiskEngineTest {
    private final RiskEngine engine = new RiskEngine();

    @Test void highValueNewInternationalTransactionIsHighRisk() {
        Transaction t = new Transaction();
        t.setAmount(90000); t.setNewDevice(true); t.setInternational(true);
        t.setCountry("SG"); t.setMerchant("Electronics");
        RiskEngine.Assessment result = engine.assess(t);
        assertEquals(100, result.score());
        assertEquals("HIGH", result.level());
        assertFalse(result.reasons().isEmpty());
    }

    @Test void ordinaryDomesticTransactionIsLowRisk() {
        Transaction t = new Transaction();
        t.setAmount(1200); t.setNewDevice(false); t.setInternational(false);
        t.setCountry("IN"); t.setMerchant("Grocery");
        RiskEngine.Assessment result = engine.assess(t);
        assertEquals(0, result.score());
        assertEquals("LOW", result.level());
    }
}

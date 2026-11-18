package com.finguard;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataSeeder implements CommandLineRunner {
    private final TransactionService service;
    public DataSeeder(TransactionService service){this.service=service;}
    @Override public void run(String... args){
        if(!service.all().isEmpty()) return;
        seed("CUS-1042",1250,"Grocery Hub","IN","DEV-01",false,false);
        seed("CUS-2091",42800,"ElectroMart","IN","DEV-NEW-2",true,false);
        seed("CUS-3308",98500,"Crypto Exchange","SG","DEV-77",true,true);
        seed("CUS-1042",8900,"Fuel Station","IN","DEV-01",false,false);
    }
    private void seed(String c,double amount,String merchant,String country,String device,boolean fresh,boolean intl){
        Transaction t=new Transaction();t.setCustomerId(c);t.setAmount(amount);t.setCurrency("INR");t.setMerchant(merchant);
        t.setCountry(country);t.setDeviceId(device);t.setNewDevice(fresh);t.setInternational(intl);service.create(t);
    }
}

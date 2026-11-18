package com.finguard;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    private final TransactionService service;
    public ApiController(TransactionService service){this.service=service;}
    @GetMapping("/transactions") public List<Transaction> transactions(){return service.all();}
    @PostMapping("/transactions") @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@Valid @RequestBody TransactionRequest r){
        Transaction t=new Transaction(); t.setCustomerId(r.customerId()); t.setAmount(r.amount()); t.setCurrency(r.currency());
        t.setMerchant(r.merchant()); t.setCountry(r.country()); t.setDeviceId(r.deviceId());
        t.setNewDevice(r.newDevice()); t.setInternational(r.international()); return service.create(t);
    }
    @PatchMapping("/transactions/{id}/review") public Transaction review(@PathVariable Long id,@RequestBody ReviewRequest r){return service.review(id,r.status());}
    @GetMapping("/dashboard") public TransactionService.Dashboard dashboard(){return service.dashboard();}
    @GetMapping("/audit") public List<AuditEvent> audit(){return service.audit();}
    public record TransactionRequest(@NotBlank String customerId,@Positive double amount,@NotBlank String currency,
        @NotBlank String merchant,@NotBlank String country,@NotBlank String deviceId,boolean newDevice,boolean international){}
    public record ReviewRequest(@NotBlank String status){}
    @ExceptionHandler(IllegalArgumentException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> badRequest(IllegalArgumentException e){return Map.of("error",e.getMessage());}
    @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> validation(MethodArgumentNotValidException e){return Map.of("error","Please provide all required fields and a positive amount.");}
}

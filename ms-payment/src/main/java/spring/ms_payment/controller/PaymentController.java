package spring.ms_payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.ms_payment.model.request.PaymentCriteria;
import spring.ms_payment.model.request.PaymentRequest;
import spring.ms_payment.model.response.PageablePaymentResponse;
import spring.ms_payment.model.response.PaymentResponse;
import spring.ms_payment.service.PaymentService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void savePayment(@Valid @RequestBody PaymentRequest request){
        paymentService.savePayment(request);
    }

    @GetMapping("/all")
    public PageablePaymentResponse getAllPayments(@RequestParam int page,
                                                  @RequestParam int count,
                                                  PaymentCriteria paymentCriteria){
        System.out.println(page + "count : " + count + "p :" + paymentCriteria);
        System.out.println(paymentService.getAllPayments(page, count, paymentCriteria));
        return paymentService.getAllPayments(page, count, paymentCriteria);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @PutMapping("/edit/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody PaymentRequest request){
        paymentService.updatePayment(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Long id){
        paymentService.deletePaymentById(id);
    }

}

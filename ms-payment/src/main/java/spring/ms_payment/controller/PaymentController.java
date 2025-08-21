package spring.ms_payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.ms_payment.model.request.PaymentRequest;
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
    public void savePayment(@RequestBody PaymentRequest request){
        paymentService.savePayment(request);
    }

    @GetMapping("/all")
    public List<PaymentResponse> getAllPayments(){
        return paymentService.getAllPayments();
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

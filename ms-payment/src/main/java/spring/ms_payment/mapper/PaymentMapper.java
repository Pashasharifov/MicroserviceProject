package spring.ms_payment.mapper;

import spring.ms_payment.entity.Payment;
import spring.ms_payment.model.request.PaymentRequest;
import spring.ms_payment.model.response.PaymentResponse;

import java.time.LocalDateTime;

public class PaymentMapper {
    public static Payment mapRequestToEntity(PaymentRequest request){
        return Payment.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();
    }
    public static PaymentResponse mapEntityToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .responseAt(LocalDateTime.now())
                .build();
    }
}

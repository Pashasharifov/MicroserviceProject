package spring.ms_payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.ms_payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

package spring.ms_payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.ms_payment.client.CountryClient;
import spring.ms_payment.entity.Payment;
import spring.ms_payment.exception.NotFoundException;
import spring.ms_payment.mapper.PaymentMapper;
import spring.ms_payment.model.request.PaymentCriteria;
import spring.ms_payment.model.request.PaymentRequest;
import spring.ms_payment.model.response.PageablePaymentResponse;
import spring.ms_payment.model.response.PaymentResponse;
import spring.ms_payment.repository.PaymentRepository;
import spring.ms_payment.service.specification.PaymentSpecification;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.net.openssl.ciphers.Encryption.DES;
import static spring.ms_payment.mapper.PaymentMapper.mapEntityToResponse;
import static spring.ms_payment.mapper.PaymentMapper.mapRequestToEntity;
import static spring.ms_payment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static spring.ms_payment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CountryClient countryClient;
    public void savePayment(PaymentRequest request){
        log.info("savePayment.started");
        countryClient.getAllAvailableCountries(request.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, request.getAmount(), request.getCurrency()), COUNTRY_NOT_FOUND_CODE ));
        paymentRepository.save(mapRequestToEntity(request));
        log.info("savePayment.success");
    }
    public PageablePaymentResponse
//    List<PaymentResponse>
    getAllPayments(int page, int count, PaymentCriteria paymentCriteria){
        log.info("getAllPayments.started");
        var pageable = PageRequest.of(page, count, Sort.by(Sort.Direction.DESC, "id"));
        var pageablePayments = paymentRepository.findAll(new PaymentSpecification(paymentCriteria), pageable);
        var payments = pageablePayments.getContent()
                .stream()
                .map(PaymentMapper::mapEntityToResponse).toList();
        log.info("getAllPayments.success");

        return PageablePaymentResponse
                .builder()
                .payments(payments)
                .hasNextPage(pageablePayments.hasNext())
                .totalElements(pageablePayments.getTotalElements())
                .totalPages(pageablePayments.getTotalPages())
                .build();
//        return paymentRepository.findAll()
//                .stream()
//                .map(PaymentMapper::mapEntityToResponse).collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long id){
        log.info("getPayment.start id: {}", id);
        Payment payment = fetchPaymentIfExist(id);
        log.info("getPayment.success id: {}", id);
        return mapEntityToResponse(payment);
    }

    public void updatePayment(Long id, PaymentRequest request){
        log.info("updatePayment.start id: {}", id);
        countryClient.getAllAvailableCountries(request.getCurrency())
                .stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, request.getAmount(), request.getCurrency()), COUNTRY_NOT_FOUND_CODE ));
        Payment payment = fetchPaymentIfExist(id);
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        paymentRepository.save(payment);
        log.info("updatePayment.success id: {}", id);
    }

    public void deletePaymentById(Long id){
        log.info("deletePayment.start id: {}", id);
        paymentRepository.deleteById(id);
        log.info("deletePayment.success id: {}", id);
    }

    private Payment fetchPaymentIfExist(Long id){
        return paymentRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}

package spring.ms_payment.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {

    @Min(value = 1)
    @Max(value = 1000)
    BigDecimal amount;

    @NotBlank(message = "description can not be null")
    String description;

    @NotBlank(message = "currency can not be null")
    String currency;

}

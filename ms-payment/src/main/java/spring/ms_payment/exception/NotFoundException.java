package spring.ms_payment.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private final String code;

    public NotFoundException(String message, String code){
        super(message);
        this.code = code;
    }

}

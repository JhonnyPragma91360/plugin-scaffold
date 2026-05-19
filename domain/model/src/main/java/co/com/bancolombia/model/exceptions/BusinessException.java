package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.BusinnessErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinnessErrorMessage errorMessage;

    public BusinessException(BusinnessErrorMessage errorMessage ) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}

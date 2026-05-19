package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.TechicalErrorMessage;
import lombok.Getter;

@Getter
public class TechicalException extends RuntimeException{

    public final TechicalErrorMessage errorMessage;

    public TechicalException( Throwable cause, TechicalErrorMessage errorMessage ){
        super(errorMessage.getMessage(),cause);
        this.errorMessage = errorMessage;
    }
}

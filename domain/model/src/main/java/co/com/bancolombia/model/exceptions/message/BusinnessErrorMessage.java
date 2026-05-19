package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinnessErrorMessage {

    INVALID_REQUEST("SF92349","Invalid request"),

    ACCOUNT_VALIDATION_ERROR("SF92350","Account validation error"),
    ACCOUNT_FIND_ERROR("SF92351","Account not found error"),
    CHANNEL_TRANSACTION_NOT_FOUND("SF92352","Channel transaction not found error"),;


    public final String code;

    public final String message;
}

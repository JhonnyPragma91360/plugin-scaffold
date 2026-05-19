package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechicalErrorMessage {

    TECHNICAL_REST_CLIENT_ERROR("SF92349","Technical Rest Client error"),

    EXTERNAL_MESSAGE_ERROR("SF92350","External message error"),;

    public final String code;

    public final String message;

}

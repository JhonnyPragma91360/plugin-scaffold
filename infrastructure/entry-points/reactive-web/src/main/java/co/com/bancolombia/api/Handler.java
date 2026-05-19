package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.RegisterAccountDto;
import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.message.BusinnessErrorMessage;
import co.com.bancolombia.usecase.registeraccount.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
// private  final UseCase useCase;
// private  final UseCase2 useCase2;

    // incialmente se inyectan los casos de uso que se van a usar en el handler, luego se crean los métodos para cada endpoint y se llama al caso de uso correspondiente
    private final RegisterAccountUseCase registerAccountUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        // Mono sirve para manejar un solo valor de manera asíncrona, en este caso se espera un Account como resultado del caso de uso
        Mono<Account> test = registerAccountUseCase.register("Jhonny", "01");
        // Retornamos una respuesta HTTP 200 con el cuerpo del Account obtenido del caso de uso, el método body() se encarga de convertir el Mono<Account> a una respuesta HTTP adecuada
        return ServerResponse.ok().body(test, Account.class);
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        // Retornamos una respuesta HTTP 200 con un cuerpo vacío, ya que este método es solo un ejemplo para mostrar cómo se pueden manejar diferentes casos de uso en el mismo handler
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        // Se retorna una respuesta HTTP 200 con un cuerpo vacío, ya que este método es solo un ejemplo para mostrar cómo se pueden manejar diferentes casos de uso en el mismo handler, en un caso real se podría retornar el resultado del caso de uso o un mensaje de éxito

        return serverRequest.bodyToMono(RegisterAccountDto.class)
                // se usa ()-> para que la funcion solo se ejecute cuando hay error
                .switchIfEmpty(Mono.error( ()-> new BusinessException(BusinnessErrorMessage.INVALID_REQUEST)))
                .flatMap(request -> registerAccountUseCase.register(request.getName(), request.getStatusId()))
                .flatMap(account -> ServerResponse.ok().bodyValue(account));

        // return ServerResponse.ok().bodyValue("");
    }
}

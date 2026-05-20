package co.com.bancolombia.consumer;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.message.BusinnessErrorMessage;
import co.com.bancolombia.model.statusaccount.StatusAccount;
import co.com.bancolombia.model.statusaccount.gateways.StatusAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestConsumer implements StatusAccountService {
    private final WebClient client;

    @Override
    public Mono<StatusAccount> getStatus(String id) {

        // Ejemplo de un retorno tipo array de objetos
       return client
               .get()
               .uri(uriBuilder -> uriBuilder.path("{id}").build(id))
               .retrieve()
               .bodyToFlux(StatusAccountDto.class) // 1. Lee el JSON como un array (Flux)
               .next()                             // 2. Toma solo el primer elemento del array y lo vuelve Mono
               .map(dto -> StatusAccount.builder()  // 3. Lo mapeas a tu objeto de dominio
                       .status(dto.getStatus())
                       .build())
               // CAPTURA EL ERROR Y RETORNA UN FLUJO DE ERROR CONTROLADO:
               .onErrorResume(throwable -> {
                   // Aquí podrías poner un log si quisieras: log.error("Error en API", throwable);
                   return Mono.error(new BusinessException(BusinnessErrorMessage.CHANNEL_TRANSACTION_NOT_FOUND));
               });


       /* // Ejemplo de un retorno de una respuesta tipo objeto
          return client
                .get()
                .uri(uriBuilder -> uriBuilder.path("{id}").build(id))
                .retrieve()
                .bodyToMono(StatusAccountDto.class)
                .map( dto -> StatusAccount.builder()
                      .status(dto.getStatus())
                      .build());

            */


         /* // Ejemplo de un retorno cualquiera para prueba
        StatusAccount prueba = StatusAccount.builder()
                .status("active")
                .build();

        return Mono.just(prueba);
            */

    }


    // These methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
    // @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/) // This name should match with settings name in application.yaml
    // public Mono<ObjectResponse> testGet() {
    //     return client
    //             .get()
    //             .retrieve()
    //             .bodyToMono(ObjectResponse.class);
    // }


// Possible fallback method
//    public Mono<String> testGetOk(Exception ignored) {
//        return client
//                .get() // TODO: change for another endpoint or destination
//                .retrieve()
//                .bodyToMono(String.class);
//    }

/* 
    @CircuitBreaker(name = "testPost") // This name should match with settings name in application.yaml
    public Mono<ObjectResponse> testPost() {
        ObjectRequest request = ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval2")
            .build();
        return client
                .post()
                .body(Mono.just(request), ObjectRequest.class)
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }
                */
}

package co.com.bancolombia.usecase.registeraccount;

import co.com.bancolombia.model.account.Account;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class RegisterAccountUseCase {


    public Mono<Account> register(String name, String statusId) {

        System.out.println("RegisterAccountUseCase: register: name: " + name + " statusId: " + statusId);

        return getStatus(name)
                // Se usa mono.zip para llamar en paralelo a las validaciones legalValidate y
                // disponibilityValidation, ya que son 2 servicios que simulan alta latencia
                .flatMap(status -> Mono.zip(legalValidate(name), disponibilityValidation())
                        // dentro de este mismo flatMap se concatena el llamado a newAccount para tener
                        // en scope el resultado de "status"
                        .map(tupla -> Account.newAccount(72L, name, status)))
                .flatMap(this::finalValidation)
                .flatMap(this::saveAccount);
    }

    private Mono<String> legalValidate(String accountName) {
        return Mono.just("ok").delayElement(Duration.ofSeconds(2));
    }

    private Mono<Integer> disponibilityValidation() {
        return Mono.just(9).delayElement(Duration.ofSeconds(2));
    }

    private Mono<String> getStatus(String accountName) {
        return Mono.just("OK");
    }

    private Mono<Account> finalValidation(Account account) {
        return Mono.just(account);
    }

    private Mono<Account> saveAccount(Account account) {
        return Mono.just(account);
    }
}

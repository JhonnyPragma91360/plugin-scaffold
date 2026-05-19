package co.com.bancolombia.accountrepository;

import co.com.bancolombia.model.account.Account;
import co.com.bancolombia.model.account.gateways.AccountRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AccountDBRepository implements AccountRepository {
    @Override
    public Mono<Account> createAccount(Account account) {
        Account newAccount = account.toBuilder().id(72L).build();
        return Mono.just(newAccount);
    }
}

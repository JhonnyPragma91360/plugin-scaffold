package co.com.bancolombia.model.account;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.message.BusinnessErrorMessage;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {

    private final Long id;
    private final String name;
    private final String status;

    public static Account newAccount( Long id, String name, String status) {

        if( name.equals("error")){
           throw new BusinessException(BusinnessErrorMessage.ACCOUNT_VALIDATION_ERROR);
        }
        return Account.builder()
                .id(id)
                .name(name)
                .status(status)
                .build();
    }
}

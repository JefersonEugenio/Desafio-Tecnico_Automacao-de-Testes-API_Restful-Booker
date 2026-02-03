package restfulBookerTests.modelos.autenticacao;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

}

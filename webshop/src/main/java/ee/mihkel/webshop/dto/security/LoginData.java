package ee.mihkel.webshop.dto.security;

import lombok.Data;

@Data
public class LoginData {
    private String personalCode;
    private String password;
}

package br.com.authentication.validation;

import br.com.authentication.exceptions.RequestLoginException;
import br.com.authentication.exceptions.SecretKeyNotFoundException;
import br.com.authentication.exceptions.TokenException;
import br.com.authentication.model.RequestLogin;
import br.com.authentication.security.type.AuthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthValidations {

    public static void validateRequestLogin(RequestLogin requestLogin) throws RequestLoginException {
        if (requestLogin.getUsername() == null || requestLogin.getUsername().isEmpty())
            throw new RequestLoginException("RequestLogin sem usuário preenchido.");
    }

    public static void validateToken(String token) throws RequestLoginException {
        if (token == null || token.isEmpty())
            throw new TokenException("Token inválido.");
    }

    public static void validateSecret(AuthConfig secretKey) throws SecretKeyNotFoundException {
        if(secretKey == null)
            throw new SecretKeyNotFoundException("Chave secreta não encontrada.");
    }

}

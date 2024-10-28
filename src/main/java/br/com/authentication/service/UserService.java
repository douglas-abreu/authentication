package br.com.authentication.service;

import br.com.authentication.exceptions.RequestLoginException;
import br.com.authentication.exceptions.SecretKeyNotFoundException;
import br.com.authentication.model.ApiResponse;
import br.com.authentication.model.JWTResponse;
import br.com.authentication.model.RequestLogin;
import br.com.authentication.security.jwt.JwtUtils;
import br.com.authentication.security.type.AuthConfig;
import br.com.authentication.validation.AuthValidations;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static br.com.authentication.validation.AuthValidations.*;


@Data
@RequiredArgsConstructor
@Service
public class UserService {
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private static AuthValidations authValidations;

	public ApiResponse<JWTResponse> authenticateUser(RequestLogin requestLogin)
			throws RequestLoginException, SecretKeyNotFoundException {

		AuthConfig secretKey = findSecretKey(requestLogin.getSecret());

		validateRequestLogin(requestLogin);
		validateSecret(secretKey);

		String jwt = jwtUtils.generateJwtTokenWithUsername(requestLogin.getUsername(), secretKey);
		JWTResponse.JWTResponseBuilder builder = partialJWTResponseBuilder(requestLogin);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "Bearer " + jwt);
		return ApiResponse.<JWTResponse>builder().status(HttpStatus.OK.value()).message("Usuário logado com sucesso.")
				.headers(responseHeaders).data(builder.authenticated(true).build()).build();

	}

	public ApiResponse<JWTResponse> getUserLogged(String token, String secret)
			throws RequestLoginException, SecretKeyNotFoundException, JWTDecodeException {

		RequestLogin requestLogin = new RequestLogin();
		AuthConfig secretKey = findSecretKey(secret);

		validateToken(token);
		validateSecret(secretKey);

		requestLogin.setUsername(jwtUtils.validateJwtToken(token, secretKey));
		JWTResponse.JWTResponseBuilder builder = partialJWTResponseBuilder(requestLogin);
		return ApiResponse.<JWTResponse>builder().status(HttpStatus.IM_USED.value())
				.message("Usuário logado encontrado!").data(builder.authenticated(true).build()).build();
	}

	public ApiResponse<Boolean> revokeToken() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Authorization", "bearer ");
		return ApiResponse.<Boolean>builder().status(HttpStatus.OK.value()).headers(responseHeaders).data(true)
				.message("Usuário deslogado com sucesso!").build();
	}

	private JWTResponse.JWTResponseBuilder partialJWTResponseBuilder(RequestLogin requestLogin) {
		return JWTResponse.builder().login(requestLogin.getUsername());
	}

	private AuthConfig findSecretKey(String secret) {
		AuthConfig secretKeyFound = null;
		for (AuthConfig systemKey: AuthConfig.values()) {
			if (systemKey.getConfig().equalsIgnoreCase(secret)) {
				secretKeyFound = systemKey;
				break;
			}
		}
		return secretKeyFound;
	}



}

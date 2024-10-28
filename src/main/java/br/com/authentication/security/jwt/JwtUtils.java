package br.com.authentication.security.jwt;


import br.com.authentication.exceptions.TokenException;
import br.com.authentication.security.type.AuthConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUtils {


	public String generateJwtTokenWithUsername(String username, AuthConfig secret) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MILLISECOND,  Integer.parseInt(AuthConfig.EXPIRE_TIME.getConfig()));
		Date expiresAt = cal.getTime();
		return JWT.create()
			.withSubject(username)
			.withIssuedAt(new Date())
			.withExpiresAt(expiresAt)
			.sign(Algorithm.HMAC512(secret.getConfig().getBytes()));
	}

	public String validateJwtToken(String token, AuthConfig secret) {
		try {
			return JWT.require(Algorithm.HMAC512(secret.getConfig().getBytes()))
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new TokenException("Token inválido.");
		}
	}

}

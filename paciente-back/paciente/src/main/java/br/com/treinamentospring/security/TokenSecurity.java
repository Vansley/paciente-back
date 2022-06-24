package br.com.treinamentospring.security;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*Método utilizado para gerar o Token*/
public class TokenSecurity {
	
	/*
	 * Método utilizado para gerar o TOKEN
	 */
	public static String generateToken(String login) {

		// chave secreta para geração do TOKEN (Evitar falsificações)
		String secretKey = JwtSecurity.SECRET;

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		// COTI_JWT -> nome da aplicação que gerou o token!
		String token = Jwts.builder().setId("COTI_JWT").setSubject(login)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 6000000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return token;
	}

	/*
	 * Método para ler o login do usuário (admin ou cliente) gravado no TOKEN
	 */
	public static String getLoginFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

		String secretKey = JwtSecurity.SECRET;
		final Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();

		return claimsResolver.apply(claims);
	}

	

}

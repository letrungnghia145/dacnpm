package com.app.helper;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
	private static final String SECRET = "SECRETKEY";
	private static final String ISSUSER = "ISSUSER";
	private static final Long VALIDITY_TOKEN_TIME = 1000 * 60 * 60 * 2L;

	private static Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	public static String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	private static Date getExpirationTimeFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public static boolean isValidToken(String token, UserDetails details) {
		return details.getUsername().equals(getUsernameFromToken(token))
				&& new Date().before(getExpirationTimeFromToken(token));
	}

	public static String generateToken(String subject) {
		return Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUSER)
				.setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TOKEN_TIME))
				.setSubject(subject).signWith(SignatureAlgorithm.HS256, SECRET).compact();
	}

	private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
}

//public String createToken() {
//	return Jwts.builder().setIssuedAt(new Date()).setIssuer("Issuser")
//			.setExpiration(new Date(System.currentTimeMillis() + 10000)).setSubject("subject")
//			.signWith(SignatureAlgorithm.HS256, "SECRET").compact();
//}
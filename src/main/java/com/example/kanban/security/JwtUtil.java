package com.example.kanban.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    private static final String SECRET = "thisisaverylongsecretkeythats256bitlong"; // Substitua com uma chave segura
    private static final long EXPIRATION_TIME = 1000 * 60 * 15; // 15 minutos

    // Método para gerar uma chave de assinatura baseada no segredo
    private byte[] getSigningKey() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(SECRET.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Erro ao gerar chave de assinatura: " + e.getMessage(), e);
        }
    }

    public String generateToken(String username) {
        // Convertendo a chave secreta para SecretKey
        SecretKey key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, key) // Chave e algoritmo
                .compact();
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(getSigningKey()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(getSigningKey()))
               .parseClaimsJws(token)
                .getBody();
    }
}
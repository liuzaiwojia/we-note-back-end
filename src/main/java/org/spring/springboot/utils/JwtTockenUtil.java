package org.spring.springboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * Created by macro on 2018/4/26.
 */
@Component
public class JwtTockenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTockenUtil.class);
    private static final String username_key = "username";
    private static final String created_key = "created";
    private String secret = "20190325";
    // 60*60*24*7七天的token有效期
    private Long expiration = 604800L;

    /**
     * @description 生成token
     * @param claim
     * @return token的字符串
     */
    private String generateToken (Map<String, Object> claim) {
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date generateExpirationDate () {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Claims getClaimsFromToken (String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.info("JWT格式验证失败：{}", token);
        }
        return claims;
    }

    public String getUserNameFromToken (String token) {
        String userName;
        try {
            Claims claims = getClaimsFromToken(token);
            userName = claims.getSubject();
        } catch (Exception e) {
            userName = null;
        }
        return userName;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return userName.equals(userDetails.getUsername());
    }

    public boolean isTokenExpired(String token) {
        Date tokenDate = getExpiredDateFromToken(token);
        return tokenDate.before(new Date());
    }

    public Date getExpiredDateFromToken (String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public String generateToken (UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(username_key, userDetails.getUsername());
        claims.put(created_key, new Date());
        return generateToken(claims);
    }

    public boolean canRefresh (String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken (String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(created_key, new Date());
        return generateToken(claims);
    }
}

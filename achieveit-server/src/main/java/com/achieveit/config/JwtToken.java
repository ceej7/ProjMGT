package com.achieveit.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Configuration
public class JwtToken {
    public JwtToken(){
        secret = "JO6HN3NGIU25G2FIG8V7VD6CK9B6T2Z5";
        expire = 6000000;
    }
    private static Logger logger = LoggerFactory.getLogger(JwtToken.class);
    public static int Illegal = 202;
    public static int Invalid = 204;
    public static int Expired = 206;
    /** 秘钥 */
    @Value("${jwt.secret}")
    public String secret;

    /** 过期时间(秒) */
    @Value("${jwt.expire}")
    public long expire;


    /**
     * 生成jwt token
     */
    public String generateToken(Long userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId + "")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] header = token.split("Bearer");
        token = header[1];
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            if(e instanceof ExpiredJwtException){
                return new Claims() {
                    @Override
                    public String getIssuer() {
                        return null;
                    }

                    @Override
                    public Claims setIssuer(String s) {
                        return null;
                    }

                    @Override
                    public String getSubject() {
                        return null;
                    }

                    @Override
                    public Claims setSubject(String s) {
                        return null;
                    }

                    @Override
                    public String getAudience() {
                        return null;
                    }

                    @Override
                    public Claims setAudience(String s) {
                        return null;
                    }

                    @Override
                    public Date getExpiration() {
                        return new Date(0);
                    }

                    @Override
                    public Claims setExpiration(Date date) {
                        return null;
                    }

                    @Override
                    public Date getNotBefore() {
                        return null;
                    }

                    @Override
                    public Claims setNotBefore(Date date) {
                        return null;
                    }

                    @Override
                    public Date getIssuedAt() {
                        return null;
                    }

                    @Override
                    public Claims setIssuedAt(Date date) {
                        return null;
                    }

                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public Claims setId(String s) {
                        return null;
                    }

                    @Override
                    public <T> T get(String s, Class<T> aClass) {
                        return null;
                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean containsKey(Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsValue(Object o) {
                        return false;
                    }

                    @Override
                    public Object get(Object o) {
                        return null;
                    }

                    @Override
                    public Object put(String s, Object o) {
                        return null;
                    }

                    @Override
                    public Object remove(Object o) {
                        return null;
                    }

                    @Override
                    public void putAll(Map<? extends String, ?> map) {

                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public Set<String> keySet() {
                        return null;
                    }

                    @Override
                    public Collection<Object> values() {
                        return null;
                    }

                    @Override
                    public Set<Entry<String, Object>> entrySet() {
                        return null;
                    }
                };
            }
            logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    // Getter && Setter
}
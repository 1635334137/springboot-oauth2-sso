package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试JWT
 *
 * @author Mr.Lan
 * @create: 2022-01-19 22:23
 */
@SpringBootTest
public class JwtTest {

    @Test
    public void createJwtToken(){
        long date = System.currentTimeMillis();
        long exp = date + 60 * 1000;
        Map<String,Object> claims = new HashMap<>();
        claims.put("name2","lanzong2");
        JwtBuilder jwtBuilder = Jwts.builder()
                //jti唯一标识ID{"id":"888"}
                .setId("888")
                //接受的用户{"sub":"Rose"}
                .setSubject("Rose")
                //iat签发时间
                .setIssuedAt(new Date())
                //使用的签名算法及密钥
                .signWith(SignatureAlgorithm.HS256,"lanzongwwer")
                //设置token失效时间
                .setExpiration(new Date(exp))
                //添加自定义申明
                .claim("name","Lanozng")
                .addClaims(claims);
        //签发token
        String token = jwtBuilder.compact();
        System.out.println(token);
    }

    @Test
    public void parseJwtToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQyNjA1MjgzLCJleHAiOjE2NDI2MDUzNDIsIm5hbWUiOiJMYW5vem5nIiwibmFtZTIiOiJsYW56b25nMiJ9.A2zahaEWl_5HwGjm6emlOWoYT2CozkPl1b93GmyYBdA";
        //解析token，获取Claims，jwt中荷载申明的对象
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey("lanzongwwer")
                .parse(token)
                .getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.get("name"));
        System.out.println(claims.get("name2"));
    }
}
